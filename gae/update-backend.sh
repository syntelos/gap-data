#!/bin/bash

sdk_dir=./appengine-java-sdk
sdk_props=${sdk_dir}/current.properties

if [ -d ${sdk_dir} ]&&[ -f ${sdk_props} ]
then
    sdk_version=$(egrep '^appengine.sdk.version=' ${sdk_props} | sed 's/.*=//')

    sdk=${sdk_dir}/${sdk_version}

    appcfg=${sdk}/bin/appcfg.sh

    if [ -x ${appcfg} ]
    then

        if [ -d war ]
        then
            ${appcfg} backends war update io
        else
            cat<<EOF>&2
Error, export directory not found 'war'
EOF
        fi
    else
        cat<<EOF>&2
Error, appcfg not found (executable) in '${appcfg}'
EOF
        exit 1
    fi
else
    cat<<EOF
Error, SDK not found in '${sdk_dir}' and '${sdk_props}'
EOF
    exit 1
fi
