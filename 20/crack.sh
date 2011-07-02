#!/bin/bash
#Used http://digitaloffense.net/tools/debian-openssl/debian_ssh_rsa_1024_x86.tar.bz2 as keyspace

myfilesize=`stat -c %s data.txt`
if [ $myfilesize = 0 ];then
   echo "Esto funciona"
fi

for i in `ls rsa/1024/*`
do
  echo "Testing $i ..."
  openssl rsautl -decrypt -inkey $i -in data.bin -out data.txt
  myfilesize=`stat -c %s data.txt`
  if [ $myfilesize != 0 ];then
    break;
  fi
done
