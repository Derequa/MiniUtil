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
if [ ! -d ./docs ]; then
    echo "Creating docs directory..."
    mkdir docs
fi
javadoc -d docs -sourcepath src -subpackages derek.util:derek.util.graph
echo "Done!"
