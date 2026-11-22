# This script check the required commands and installs necessary npm packages

# Check if npm and node are installed
if ! command -v npm &> /dev/null
then
    echo "npm could not be found, please install it first."
    exit
fi

if ! command -v node &> /dev/null
then
    echo "node could not be found, please install it first."
    exit
fi

# run npm install to install required packages in package.json
npm install