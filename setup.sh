#!/bin/sh

FORGE="1.6.4-9.11.1.920"

echo $FORGE
if [ -d forge ];
then
echo "forge exist deleting it (this is to make sure it's the correct version!)"
rmdir -rf forge 
else
echo "no forge folder"
fi 
echo "downloading forge version:" $FORGE
curl -C - -O "http://files.minecraftforge.net/minecraftforge/minecraftforge-src-"$FORGE".zip"
echo "unziping forge"
unzip "minecraftforge-src-"$FORGE".zip"
echo "running forge install"
cd forge
python install.py
cd ..
if [ -d eclipse ];
then
echo "eclipse folder already there not uziping it agian"
else
unzip eclipse.zip
fi
