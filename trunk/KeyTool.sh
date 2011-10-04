#!/bin/bash

cp=$(ls types/lib/appengine-api*.jar)
if [ -n "${cp}" ]&&[ -f "${cp}" ]
then
 cp=".:${cp}"
 java -cp ${cp} KeyTool $*
else
 cat<<EOF>&2
Missing class path ${cp}
EOF
 exit 1
fi
