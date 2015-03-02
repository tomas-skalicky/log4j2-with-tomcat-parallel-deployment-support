#!/usr/bin/env bash

war_file_path=$1
parallel_deployment_timestamp=$2

if [[ -z $war_file_path ]]; then
    printf 'ERROR: Path of WAR file needs to be passed\n'
    exit 1
fi
if [[ -z $parallel_deployment_timestamp ]]; then
    printf 'ERROR: Timestamp used by tomcat parallel deployment as an unique identifier of app version needs to be passed\n'
    exit 1
fi

echo $war_file_path $parallel_deployment_timestamp