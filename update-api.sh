#!/bin/bash
#
# On change to appengine api, update copies.
#

src=$(find gae/appengine-java-sdk -type f -name 'appengine-api-*-sdk-*.jar' | sort | tail -n 1)
srcfn=$(basename ${src})

for existing in $(find . -type f -name 'appengine-api-*-sdk-*.jar' | egrep '/lib/' | egrep -v '/appengine-java-sdk/')
do
  existingd=$(dirname ${existing})
  existingfn=$(basename ${existing})
  if [ ${srcfn} = ${existingfn} ]
  then
    echo "Up to date ${existingd}"
  else
      if svn delete ${existing}
      then
	  if cp -p ${src} ${existingd}
	  then
	      if svn add ${existingd}/${srcfn}
	      then
		  echo "Updated ${existingd}"
	      else
		  echo "Error in 'svn add ${existingd}/${srcfn}'."
		  exit 1
	      fi
	  else
	      echo "Error in 'cp -p ${src} ${existingd}'."
	      exit 1
	  fi
      else
	  echo "Error in 'svn delete ${existing}'."
	  exit 1
      fi
  fi
done
