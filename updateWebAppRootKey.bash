#!/usr/bin/env bash

web_xml_filepath=$1
new_web_app_root_key=$2
war_file_path=$3

if [[ -z $web_xml_filepath ]]; then
    printf 'ERROR: Path of web.xml file needs to be passed\n'
    exit 1
fi
if [[ -z $new_web_app_root_key ]]; then
    printf 'ERROR: New value of webAppRootKey needs to be passed\n'
    exit 1
fi
if [[ -z $war_file_path ]]; then
    printf 'ERROR: Path of WAR file needs to be passed\n'
    exit 1
fi

echo $web_xml_filepath $new_web_app_root_key $war_file_path