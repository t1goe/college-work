import praw
from praw.models import MoreComments
import nltk
from nltk.util import ngrams
import emoji
import os
from nltk.corpus import stopwords
import matplotlib.pyplot as plt


# nltk.download('stopwords')


# Remove all emojis from string
def remove_emojis(text):
    return text.encode('ascii', 'ignore').decode('ascii')


# Convert all emojis in a string to :string_representation:
def decode_emojis(text):
    return emoji.demojize(text)


# Recursively display all comments
def display_comments(comments, indent=" "):
    for reply in comments:
        if not hasattr(reply, 'body'):
            return

        tokens = nltk.word_tokenize(reply.body)

        print(indent + " " + reply.body + "(length: " + str(len(tokens)) + ")")
        display_comments(reply.replies, indent + "\t")


# Lexical complexity of a list of tokens
def lexical_complexity(tokens):
    # tokens = nltk.word_tokenize(text)
    return len(set(tokens)) / len(tokens)


# Average lex diversity of a set of comments
def average_individual_lex_complexity(comments):
    diversity_scores = []

    for reply in comments:
        if not hasattr(reply, 'body'):
            return diversity_scores

        tokens = nltk.word_tokenize(reply.body)
        diversity_scores.append(lexical_complexity(tokens))
        print(tokens)
        print(lexical_complexity(tokens))
        print()

        temp_diversity_list = average_individual_lex_complexity(reply.replies)

        diversity_scores.extend(temp_diversity_list)

    return diversity_scores


# Builds a corpus of top level comments for a subreddit
def build_subreddit_corpus(subreddit, reddit):
    post_limit = 300

    if not os.path.exists(subreddit):
        os.makedirs(subreddit)

    negative = open(subreddit + "/negative.txt", "w", encoding='utf8')
    one = open(subreddit + "/gt1.txt", "w", encoding='utf8')
    ten = open(subreddit + "/gt10.txt", "w", encoding='utf8')
    hundred = open(subreddit + "/gt100.txt", "w", encoding='utf8')
    thousand = open(subreddit + "/gt1000.txt", "w", encoding='utf8')
    tenthousand = open(subreddit + "/gt10000.txt", "w", encoding='utf8')
    overall = open(subreddit + "/all.txt", "w", encoding='utf8')

    # on the following line you can change top to any of the previously mentioned ways of sorting
    # and the limit to however many posts you would like to extract (here we extract just 10).

    count = 0
    for post in reddit.subreddit(subreddit).top(limit=post_limit):
        count = count + 1
        print("Working on post " + str(count) + "/" + str(post_limit) + " in " + subreddit)

        # this section collects the comments
        for comment in post.comments:

            if isinstance(comment, MoreComments):
                continue

            formatted_comment = decode_emojis(comment.body) + "\n"
            try:
                overall.write(formatted_comment)

                if comment.score >= 10000:
                    tenthousand.write(formatted_comment)
                elif comment.score >= 1000:
                    thousand.write(formatted_comment)
                elif comment.score >= 100:
                    hundred.write(formatted_comment)
                elif comment.score >= 10:
                    ten.write(formatted_comment)
                elif comment.score >= 1:
                    one.write(formatted_comment)
                else:
                    negative.write(formatted_comment)
            except UnicodeEncodeError:
                print("String contains character causing errors:")
                print(formatted_comment)

    negative.close()
    one.close()
    ten.close()
    hundred.close()
    thousand.close()
    tenthousand.close()
    overall.close()


# Shows the score distribution of comments in
def score_distribution(subreddit, reddit):
    post_limit = 300

    count = 0
    scores = [0] * 1000
    for post in reddit.subreddit(subreddit).top(limit=post_limit):
        count = count + 1
        print("Working on post " + str(count) + "/" + str(post_limit) + " in " + subreddit)

        # this section collects the comments
        for comment in post.comments:

            if isinstance(comment, MoreComments):
                continue

            a = 100

            scores[int(comment.score / a)] = scores[int(comment.score / a)] + 1

    x_axis = range(len(scores))
    y_axis = scores
    # print(x_axis)
    # print(y_axis)
    plt.bar(x_axis, y_axis, color='red')
    plt.show()


# Summarises the lexical diversity of a sub at various scores
def lexical_diversity_summary(sub_directory):
    if not os.path.exists(sub_directory):
        print("Subreddit directory " + sub_directory + " does not exist")
        return

    negative = open(sub_directory + "/negative.txt", "r", encoding="utf8")
    one = open(sub_directory + "/gt1.txt", "r", encoding="utf8")
    ten = open(sub_directory + "/gt10.txt", "r", encoding="utf8")
    hundred = open(sub_directory + "/gt100.txt", "r", encoding="utf8")
    thousand = open(sub_directory + "/gt1000.txt", "r", encoding="utf8")
    tenthousand = open(sub_directory + "/gt10000.txt", "r", encoding="utf8")
    overall = open(sub_directory + "/all.txt", "r", encoding="utf8")

    sample_size = len(custom_tokenize(tenthousand.read()))
    tenthousand = open(sub_directory + "/gt10000.txt", "r", encoding="utf8")

    print("Lexical diversity of all comments:\t" +
          str(lexical_complexity(custom_tokenize(overall.read())[:sample_size])))
    print("Lexical diversity of posts w score >10,000:\t" +
          str(lexical_complexity(custom_tokenize(tenthousand.read())[:sample_size])))
    print("Lexical diversity of posts w score >1,000:\t" +
          str(lexical_complexity(custom_tokenize(thousand.read())[:sample_size])))
    print("Lexical diversity of posts w score >100:\t" +
          str(lexical_complexity(custom_tokenize(hundred.read())[:sample_size])))
    print("Lexical diversity of posts w score >10:\t\t" +
          str(lexical_complexity(custom_tokenize(ten.read())[:sample_size])))
    print("Lexical diversity of posts w score >1:\t\t" +
          str(lexical_complexity(custom_tokenize(one.read())[:sample_size])))

    negative.close()
    one.close()
    ten.close()
    hundred.close()
    thousand.close()
    tenthousand.close()
    overall.close()


# Tokenize the words while doing preprocessing.
def custom_tokenize(text):
    tokens = nltk.word_tokenize(text)
    lowercase = [t.lower() for t in tokens]
    no_punct = [t for t in lowercase if t.isalnum()]
    # no_stop = [t for t in no_punct if t not in stopwords.words('english')]
    return no_punct


# Get the Q most common ngrams of a text
def common_ngrams(text, n, q):
    tokens = custom_tokenize(text)
    gram_fd = nltk.FreqDist(nltk.ngrams(tokens, n))
    return gram_fd.most_common(q)


# Summarize the common n-grams of a subreddit at different scores
def common_ngram_summary(sub_directory):
    if not os.path.exists(sub_directory):
        print("Subreddit directory " + sub_directory + " does not exist")
        return

    ngram_min = 1
    ngram_max = 4

    ngram_quantity = 40

    for x in range(ngram_min, ngram_max + 1):
        negative = open(sub_directory + "/negative.txt", "r", encoding="utf8")
        one = open(sub_directory + "/gt1.txt", "r", encoding="utf8")
        ten = open(sub_directory + "/gt10.txt", "r", encoding="utf8")
        hundred = open(sub_directory + "/gt100.txt", "r", encoding="utf8")
        thousand = open(sub_directory + "/gt1000.txt", "r", encoding="utf8")
        tenthousand = open(sub_directory + "/gt10000.txt", "r", encoding="utf8")
        overall = open(sub_directory + "/all.txt", "r", encoding="utf8")

        print("Most common " + str(x) + "-grams of all posts:\t" +
              str(common_ngrams(overall.read(), x, ngram_quantity)))
        print("Most common " + str(x) + "-grams of posts w score >10,000:\t" +
              str(common_ngrams(tenthousand.read(), x, ngram_quantity)))
        print("Most common " + str(x) + "-grams of posts w score >1,000:\t" +
              str(common_ngrams(thousand.read(), x, ngram_quantity)))
        print("Most common " + str(x) + "-grams of posts w score >100:\t" +
              str(common_ngrams(hundred.read(), x, ngram_quantity)))
        print("Most common " + str(x) + "-grams of posts w score >10:\t" +
              str(common_ngrams(ten.read(), x, ngram_quantity)))
        print("Most common " + str(x) + "-grams of posts w score >1:\t" +
              str(common_ngrams(one.read(), x, ngram_quantity)))
        print()

    negative.close()
    one.close()
    ten.close()
    hundred.close()
    thousand.close()
    tenthousand.close()
    overall.close()


ci = "f2CDXIEJMw9amg"  # your client id
cs = "dS2I4meWoXMgQ-AtZObOO8C5WM_smw"  # your client secret
ua = "HLTUCDScript"  # your user agent name
sub = "todayilearned"  # the name of the subreddit (not including the 'r/')

# nltk.download('punkt')

reddit = praw.Reddit(
    client_id=ci,
    client_secret=cs,
    user_agent=ua
)

subreddits = [
    "todayilearned",
    "funny",
    "science",
    "gaming",
    "leagueoflegends"
]

# for r in subreddits:
#     print(r)
#     build_subreddit_corpus(r, reddit)

# build_subreddit_corpus("funny", reddit)

common_ngram_summary("todayilearned")
# lexical_diversity_summary("todayilearned")
# score_distribution("todayilearned", reddit)

# on the following line you can change top to any of the previously mentioned ways of sorting
# and the limit to however many posts you would like to extract (here we extract just 10).
# for post in reddit.subreddit(sub).top(limit=1):
#     d = average_individual_lex_complexity(post.comments)
#     print(sum(d) / len(d))

# with open(sub + ".txt", "w") as f:
#     # on the following line you can change top to any of the previously mentioned ways of sorting
#     # and the limit to however many posts you would like to extract (here we extract just 10).
#     for post in reddit.subreddit(sub).top(limit=1):
#         display_comments(post.comments)
#
#         # this line collects the post titles
#         f.write(post.title + "\n")
#
#         # this line collects the post content
#         f.write(post.selftext + "\n")
#
#         # this section collects the comments
#         for comment in post.comments:
#             if isinstance(comment, MoreComments):
#                 continue
#
#             f.write(decode_emojis(comment.body) + "\n")
