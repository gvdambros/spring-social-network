cd $(dirname $0)
cd ../complete

mvn clean package

rm -rf target

rm -rf build
exit
