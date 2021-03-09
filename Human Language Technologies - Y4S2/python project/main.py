import praw
from praw.models import MoreComments
import nltk
import emoji
import os


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


def build_subreddit_corpus(subreddit, reddit):
    post_limit = 1

    if not os.path.exists(subreddit):
        os.makedirs(subreddit)

    ten = open(subreddit + "/gt10^1.txt", "w")
    hundred = open(subreddit + "/gt10^2.txt", "w")
    thousand = open(subreddit + "/gt10^3.txt", "w")
    tenthousand = open(subreddit + "/gt10^4.txt", "w")
    overall = open(subreddit + "/all.txt", "w")

    # on the following line you can change top to any of the previously mentioned ways of sorting
    # and the limit to however many posts you would like to extract (here we extract just 10).
    for post in reddit.subreddit(subreddit).top(limit=post_limit):

        # this section collects the comments
        for comment in post.comments:
            if isinstance(comment, MoreComments):
                continue

            if comment.score >= 10000:
                tenthousand.write(decode_emojis(comment.body) + "\n")
            elif comment.score >= 1000:
                thousand.write(decode_emojis(comment.body) + "\n")
            elif comment.score >= 100:
                hundred.write(decode_emojis(comment.body) + "\n")
            elif comment.score >= 10:
                ten.write(decode_emojis(comment.body) + "\n")

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

build_subreddit_corpus(sub, reddit)

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
