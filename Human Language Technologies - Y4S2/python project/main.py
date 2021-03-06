ci = "f2CDXIEJMw9amg"  # your client id
cs = "dS2I4meWoXMgQ-AtZObOO8C5WM_smw"  # your client secret
ua = "HLTUCDScript"  # your user agent name
sub = "todayilearned"  # the name of the subreddit (not including the 'r/')

import praw
from praw.models import MoreComments

reddit = praw.Reddit(
    client_id=ci,
    client_secret=cs,
    user_agent=ua
)


def remove_emojis(text):
    return text.encode('ascii', 'ignore').decode('ascii')

with open(sub + ".txt", "w") as f:
    # on the following line you can change top to any of the previously mentioned ways of sorting
    # and the limit to however many posts you would like to extract (here we extract just 10).
    for post in reddit.subreddit(sub).top(limit=10):

        # this line collects the post titles
        f.write(post.title + "\n")

        # this line collects the post content
        f.write(post.selftext + "\n")

        # this section collects the comments
        for comment in post.comments:
            if isinstance(comment, MoreComments):
                continue

            f.write(remove_emojis(comment.body) + "\n")
