// initialize global variable
let score = 0;
// if state = N, N+1th problem
let state = 0;

// 5 Questions, Answers(A~D) data
const data = [
    ["Which one is NOT a legal variable name?", "_myvar", "Myvar", "my_var", "my-var"],
    ["What is a correct syntax to output \"Hello World\" in Python?", "print(\"Hello World\")", "p(\"Hello World\")", "echo\"Hello World\";", "echo \"Hello World\""],
    ["How do you insert COMMENTS in Python code?", "/*This is a comment*/", "#This is a comment", "//This is a comment", "None of the above"],
    ["What is the correct syntax to output the type of a variable of object in Python?", "print(typeof(x))", "print(typeof x)", "print(typeOf(x))", "print(type(x))"],
    ["What is the correct file extension for Python files?", ".pt", ".pyt", ".pyth", ".py"],
];

// answer data, A~D is 1~4
const answer = [
    1, 1, 2, 4, 4
];

// init when start&play again
// permutation[i]=j means i+1th question is index j in data
// selected[i]=1 means index i question is included (for not redundant question set)
var permutation = [
    0, 0, 0, 0
];
var selected = [
    0, 0, 0, 0, 0
];

// change page id str1 to str2
function changepg(str1,str2) {
    document.getElementById(str1).style.display = "none";
    document.getElementById(str2).style.display = "block";
}

// choose 4 random number between 0~4
function init_permutation() {
    for (var i = 0; i < 5; i++) selected[i]=0;
    for (var i = 0; i < 4; i++) {
        while (1) {
            var rnd = Math.floor(Math.random() * Math.floor(5));
            if (selected[rnd] == 0) {
                selected[rnd] = 1; permutation[i]=rnd;
                break;
            }
        }
    }
}

// if problem changes, change text
function set_problem(qnum) {
    document.getElementById("Iquestion").textContent = data[qnum][0];
    document.getElementById("Ianswer1").textContent = data[qnum][1];
    document.getElementById("Ianswer2").textContent = data[qnum][2];
    document.getElementById("Ianswer3").textContent = data[qnum][3];
    document.getElementById("Ianswer4").textContent = data[qnum][4];
}

// execute when start, check answer
function click1(sel) {
    // if pressed start button
    if (sel == -1) {
        init_permutation();
        set_problem(permutation[state]);
        changepg("startpg", "question");
    }
    // if selected 1~4 question's answer
    else {
        var strid = "Ianswer" + sel.toString();
        if (answer[permutation[state]] == sel) {
            score++;
            document.getElementById("Iscore").textContent = score.toString();
            document.getElementById(strid).style = "background-color:green";
        } else {
            document.getElementById(strid).style = "background-color:red";
        }
        state++;
        if (state < 4) {
            setTimeout(function() {
                document.getElementById("Iquestionnum").textContent = "Question "+ (state+1).toString() + "/4";
                set_problem(permutation[state]);
                document.getElementById("Iprogress").style = "width:"+(25*(state+1)).toString()+"%";
                document.getElementById(strid).style = "background-color:white";
            }, 1000);
        }
    }
    // if solved 4 questions
    if (state == 4) {
        setTimeout(function() {
            document.getElementById("Iresult").textContent = "Total score: " + score.toString();
            document.getElementById(strid).style = "background-color:white";
            changepg("question", "endpg");
        }, 1000);
    }
}

// if pressed play again button
function restart() {
    state = 0; score = 0;
    init_permutation();
    document.getElementById("Iquestionnum").textContent = "Question "+ (state+1).toString() + "/4";
    document.getElementById("Iscore").textContent = score.toString();
    document.getElementById("Iprogress").style = "width:25%";
    set_problem(permutation[state]);
    changepg("endpg", "question");
}