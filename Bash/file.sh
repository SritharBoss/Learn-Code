#!/bin/bash


#!/bin/bash

getopts "n" opt

noupdate=$OPTARG


arg=$1

echo "ARG = $arg, noupdate=$noupdate"

if [ -n "$arg" ]; then

        path=$(which $arg)

        if [ -n "$path" ];then
                echo "Package already installed"
        elif [ "$path" = "" && "$noupdate" = "?" ];then
                        #sudo apt update && sudo apt install -y $arg
        elif [ "$conf" = "N" -o "$conf" = "n" -o "$conf" = "No" -o "$conf" = "no" ];then
                        #sudo apt install -y $arg
        fi
else
    echo "Please provide the package name to install"
fi

#echo "$(echo $b | xxd -p | sed -e 's/\(..\)/:\1/g' -e 's/^://')"
