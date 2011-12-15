#!/bin/bash

zipf=$(ls appengine-java-sdk-*.zip | tail -n 1)
version=$(echo $zipf | awk -F- '{print $4}' | sed 's/\.zip//')
echo $version
if [ -d ${version} ]|| unzip ${zipf}
then
 cat<<EOF>current.properties
appengine.sdk.version=$version
EOF
 vdir=appengine-java-sdk-$version
 if [ -d ${version} ]|| mv ${vdir} ${version}
 then

  rm -rf $(find ${version} -type d -name orm )

  if [ -d ${version}/docs/javadoc ]
  then
   rm -rf javadocs
   mv ${version}/docs/javadoc javadocs
   for each in ${version}/docs/{remoteapi,testing,tools}
   do
    name=$(basename ${each})
    rm -rf ${name}
    mv ${each}/javadoc ${name}
   done
  fi

  if [ -d ${version}/demos ]
  then
   rm -rf ${version}/demos
  fi

  rm -f ${version}/*.ORM

  cd ..

  tar cf appengine-java-sdk-${version}.tar appengine-java-sdk/${version} appengine-java-sdk/current.properties

  tar tf appengine-java-sdk-${version}.tar
  ls -l appengine-java-sdk-${version}.tar

 else
  cat<<EOF>&2
Error moving directory ${vdir} to ${version}
EOF
  exit 1
 fi
else
 cat<<EOF>&2
Error unzipping ${zipf}
EOF
 exit 1
fi
