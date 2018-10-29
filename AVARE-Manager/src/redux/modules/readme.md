This folder contains the modules of the redux-store. Each module represents one of the state attributes. 
Currently there are two modules: apps and categories. The redux-store will save two arrays, one for existing apps and another one for the categories. 

For each module there are two files: 
    action.js describes the possible actions that could happen, like "add App x" or "change State of App y". 

    reducer.js then handles the action and computes the next state of the redux-store 

More modules could be added in the future to handle more possible states of the avare-manager that should be saved in the redux-store. 