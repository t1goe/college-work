ci = "f2CDXIEJMw9amg"  # your client id
cs = "dS2I4meWoXMgQ-AtZObOO8C5WM_smw"  # your client secret
ua = "HLTUCDScript"  # your user agent name
sub = "todayilearned"  # the name of the subreddit (not including the 'r/')

import praw
from praw.models import MoreComments
import nltk
import emoji

# nltk.download('punkt')

reddit = praw.Reddit(
    client_id=ci,
    client_secret=cs,
    user_agent=ua
)


def remove_emojis(text):
    return text.encode('ascii', 'ignore').decode('ascii')


def decode_emojis(text):
    return emoji.demojize(text)


def display_comments(comments, indent=" "):
    for reply in comments:
        if not hasattr(reply, 'body'):
            return

        tokens = nltk.word_tokenize(reply.body)

        print(indent + " " + reply.body + "(length: " + str(len(tokens)) + ")")
        display_comments(reply.replies, indent + "\t")


def lexical_complexity(tokens):
    # tokens = nltk.word_tokenize(text)
    return len(set(tokens)) / len(tokens)


def average_individual_lex_complexity(comments):
    diversity_scores = []
    number_of_comments = 0

    for reply in comments:
        if not hasattr(reply, 'body'):
            return diversity_scores, number_of_comments

        tokens = nltk.word_tokenize(reply.body)
        diversity_scores.append(lexical_complexity(tokens))
        print(tokens)
        print(lexical_complexity(tokens))
        print()

        number_of_comments = number_of_comments + 1

        temp_diversity_list, temp_number_comments = average_individual_lex_complexity(reply.replies)

        diversity_scores.extend(temp_diversity_list)
        number_of_comments = number_of_comments + temp_number_comments

    return diversity_scores, number_of_comments

def average_individual_lex_complexity(comments):
    diversity_scores = []
    number_of_comments = 0

    for reply in comments:
        if not hasattr(reply, 'body'):
            return diversity_scores, number_of_comments

        tokens = nltk.word_tokenize(decode_emojis(reply.body))
        diversity_scores.append(lexical_complexity(tokens))
        print(tokens)
        print(lexical_complexity(tokens))
        print()

        number_of_comments = number_of_comments + 1

        temp_diversity_list, temp_number_comments = average_individual_lex_complexity(reply.replies)

        diversity_scores.extend(temp_diversity_list)
        number_of_comments = number_of_comments + temp_number_comments

    return diversity_scores, number_of_comments


# on the following line you can change top to any of the previously mentioned ways of sorting
# and the limit to however many posts you would like to extract (here we extract just 10).
for post in reddit.subreddit(sub).top(limit=1):
    d, n  = average_individual_lex_complexity(post.comments)
    print(sum(d)/n)

with open(sub + ".txt", "w") as f:
    # on the following line you can change top to any of the previously mentioned ways of sorting
    # and the limit to however many posts you would like to extract (here we extract just 10).
    for post in reddit.subreddit(sub).top(limit=1):
        display_comments(post.comments)

        # this line collects the post titles
        f.write(post.title + "\n")

        # this line collects the post content
        f.write(post.selftext + "\n")

        # this section collects the comments
        for comment in post.comments:
            if isinstance(comment, MoreComments):
                continue

            f.write(decode_emojis(comment.body) + "\n")
