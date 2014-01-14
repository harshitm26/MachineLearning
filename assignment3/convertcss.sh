#!/bin/sh

sed s/[0-9]*://g<satimage.scale.t>temp
sed 's/\(^[0-9]*\ \)\(.*\)/\2\1/'<temp>temp1.csv
#cat temp
