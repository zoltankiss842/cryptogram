# Using IntelliJ

[Video here](https://drive.google.com/file/d/1b2KHySTOD7iJb-kPtCLpwOhHFoUwxQMM/view?usp=sharing)

# Iteration 1

After class diagrams have been submitted, a sample solution will be released. This **must be used as the basis** for your implementation. All .java files, including
junit java files, for each iteration should be submitted to MyPlace in addition to the commits to the gitlab working repository. Submission to MyPlace is to
ensure a timestamped copy of code at the point of submission for each iteration.

![image](resources/README/basis.png)

## Handling merge conflicts

I have made a quick video about how to handle conflicts and how to rebase branches:
[Handle conflicts](https://drive.google.com/file/d/13ji-xoE88SMhPHNP4D1VkwHOimDWb4Gx/view?usp=sharing)

## How to maintain the directory system

1. We sort classes into packages. In directory `src`, we create a `main` and a `test` package.
Both package will contain the same packages.
2. In the `main` package, we create 3 packages: `main.cryptogram`, `game`, `players`. We add these packages into the `test` package as well.
3. Each package will contain the appropriate Java classes.

See example: (Note: these are packages and not directories)
```
src/
├── main/
│   ├── main.cryptogram/
│   │   └── ...
│   │   
│   ├── game/
│   │   └── ...
│   │   
│   └── players/
│       └── ...
│       
└── tests/
    ├── main.cryptogram/
    │   └── ...
    │   
    ├── game/
    │   └── ...
    │   
    └── players/
        └── ...
        
```
