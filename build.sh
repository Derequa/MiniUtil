echo "Building source and tests..."
if [ ! -d ./bin ]; then
    echo "Creating bin directory..."
    mkdir bin
fi
echo "Running Ninja..."
ninja make
echo "Building TestRunner..."
ninja makeTestRunner
echo "Running tests..."
ninja tests
echo "Generating Documentation..."
if [ ! -d ./doc ]; then
    echo "Creating doc directory..."
    mkdir doc
fi
javadoc -d doc -sourcepath src -subpackages derek.util:derek.util.graph
echo "Done!"
