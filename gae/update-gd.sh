#!/bin/bash

srcd=${HOME}/src/gap-data/trunk

is_svn=false
is_git=false
if [ -d .svn ]
then
    is_svn=true
elif [ -d .git ]
then
    is_git=true
fi

function nameof {
    basename ${1} | sed 's/\..*//; s/[0-9]//;'
}

#(odl)

for tgt in $(find lib/odl -type f -name '*.odl')
do
    tgtd=$(dirname ${tgt})
    name=$(nameof ${tgt})
    
    for src in $(find ${srcd} -type f -name "${name}*.odl" )
    do
        if [ ${name} = $(nameof ${src}) ]
        then
            new=${tgtd}/$(basename ${src})
            if [ ! -f ${new} ]||[ ${src} -nt ${new} ]
            then
                if ${is_git}
                then
                    set -x
                    if [ "${new}" != "${tgt}" ]
                    then
                        git rm -f "${tgt}"
                        cp -p ${src} ${new}
                        git add "${new}"
                    else
                        cp -p ${src} ${new}
                    fi
                    set +x
                elif ${is_svn}
                then
                    set -x
                    if [ "${new}" != "${tgt}" ]
                    then
                        svn delete "${tgt}"
                        cp -p ${src} ${new}
                        svn add "${new}"
                    else
                        cp -p ${src} ${new}
                    fi
                    set +x
                else
                    rm -f ${tgt} 
                    cp -p ${src} ${tgtd}
                    ls -l ${new}
                fi
            fi
        fi
    done
done
