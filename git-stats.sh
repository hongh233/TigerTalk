#!/bin/bash
git-stats() {
    author=${1-`git config --get user.name`}

    echo "Commit stats for $author:"
    git log --shortstat --author "$author" -i 2> /dev/null \
        | grep -E 'files? changed' \
        | awk 'BEGIN{commits=0;inserted=0;deleted=0} \
            {commits+=1; if($5!~"^insertion") { deleted+=$4 } \
            else { inserted+=$4; deleted+=$6 } } END \
            {print "Commits:", commits \
            "\nLines inserted:", inserted, \
            "\nLines deleted:", deleted}'
}

## user name is broken, please use email
git-stats "<hn582183@dal.ca>"
git-stats "<oegbor@dal.ca>"
git-stats "<bn530336@dal.ca>"
git-stats "<sh673592@dal.ca>"
git-stats "<th811292@dal.ca>"