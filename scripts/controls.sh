#!/usr/bin/env bash

APP_LOCATION=""  # path to jar file e.g. /your/path/here
LOG_FILE=""      # path to log file, including log name e.g. /your/path/here/ws.log
APP_EXE_NAME=""  # jar file name e.g. myjar.jar
configured=0

if [[ -z ${LOG_FILE} ]];then
    echo "LOG_FILE path and name not supplied."
    configured=1
fi
if [[ -z ${APP_LOCATION} ]];then
    echo "APP_LOCATION path not supplied."
    configured=1
fi
if [[ -z ${APP_EXE_NAME} ]];then
    echo "Jar name as APP_EXE_NAME not supplied."
    configured=1
fi
if [[ ${configured} == 1 ]];then
    echo "Edit to configure this script before running."
    exit 1
fi

case $1 in
    start)
        nohup java -jar ${APP_LOCATION}/${APP_EXE_NAME} --spring.profiles.active=prod > ${LOG_FILE} 2>&1 &
        ;;
    stop)
        PID=$(ps -ef | grep ${APP_EXE_NAME} | grep -v grep | awk '{print $2}')
        if [[ -z "${PID}" ]];then
            echo "Application is already stopped"
        else
            echo kill ${PID}
            kill ${PID}
        fi
        ;;
    *)
        echo "Valid options are [start|stop]"
        ;;
esac
