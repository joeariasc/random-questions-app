package com.spotapp.mobile.ui.feature.home

import com.spotapp.mobile.data.sources.remote.firestore.model.Option
import com.spotapp.mobile.data.sources.remote.firestore.model.Question
import java.util.UUID

fun generateQuestions(): List<Question> {
    return listOf(
        Question(
            id = UUID.randomUUID().toString(),
            questionText = "What is the purpose of an API (Application Programming Interface) in software development?",
            options = listOf(
                Option(1, "Automated Processing Interface"),
                Option(2, "Algorithmic Program Instruction"),
                Option(3, "Application Programming Interface"),
                Option(4, "Advanced Program Integration")
            ),
            idCorrectOption = 3
        ),
        Question(
            id = UUID.randomUUID().toString(),
            questionText = "In the context of databases, what does SQL stand for?",
            options = listOf(
                Option(1, "Structured Query Language"),
                Option(2, "Standard Query Language"),
                Option(3, "Sequential Query Logic"),
                Option(4, "Systematic Question Logic")
            ),
            idCorrectOption = 2
        ),
        Question(
            id = UUID.randomUUID().toString(),
            questionText = "Which programming language is commonly used for developing server-side web applications?",
            options = listOf(
                Option(1, "JavaScript"),
                Option(2, "Python"),
                Option(3, "Java"),
                Option(4, "Ruby")
            ),
            idCorrectOption = 3
        ),
        Question(
            id = UUID.randomUUID().toString(),
            questionText = "What is the purpose of the 'break' statement in programming?",
            options = listOf(
                Option(1, "Terminate the program"),
                Option(2, "Skip the current iteration in a loop"),
                Option(3, "Execute a block of code"),
                Option(4, "Define a function")
            ),
            idCorrectOption = 2
        ),
        Question(
            id = UUID.randomUUID().toString(),
            questionText = "Which of the following is a commonly used version control system for tracking changes in source code?",
            options = listOf(
                Option(1, "SVN"),
                Option(2, "Git"),
                Option(3, "Mercurial"),
                Option(4, "Perforce")
            ),
            idCorrectOption = 2
        ),
        Question(
            id = UUID.randomUUID().toString(),
            questionText = "What is the purpose of the 'alt' attribute in HTML image tags?",
            options = listOf(
                Option(1, "Specify image file format"),
                Option(2, "Define image size"),
                Option(3, "Provide alternative text for accessibility"),
                Option(4, "Link to another web page")
            ),
            idCorrectOption = 3
        ),
        Question(
            id = UUID.randomUUID().toString(),
            questionText = "Which data type is used to store whole numbers in many programming languages?",
            options = listOf(
                Option(1, "Float"),
                Option(2, "String"),
                Option(3, "Integer"),
                Option(4, "Boolean")
            ),
            idCorrectOption = 3
        ),
        Question(
            id = UUID.randomUUID().toString(),
            questionText = "What is the primary function of CSS (Cascading Style Sheets) in web development?",
            options = listOf(
                Option(1, "Server-side scripting"),
                Option(2, "Database management"),
                Option(3, "User authentication"),
                Option(4, "Styling and formatting web content")
            ),
            idCorrectOption = 4
        ),
        Question(
            id = UUID.randomUUID().toString(),
            questionText = "Which programming paradigm is focused on the concept of objects?",
            options = listOf(
                Option(1, "Procedural programming"),
                Option(2, "Functional programming"),
                Option(3, "Object-oriented programming"),
                Option(4, "Logic programming")
            ),
            idCorrectOption = 3
        ),
        Question(
            id = UUID.randomUUID().toString(),
            questionText = "What does the term 'bug' refer to in software development?",
            options = listOf(
                Option(1, "A feature enhancement"),
                Option(2, "A programming error or flaw"),
                Option(3, "A user interface design"),
                Option(4, "A hardware malfunction")
            ),
            idCorrectOption = 2
        ),
        Question(
            id = UUID.randomUUID().toString(),
            questionText = "Which of the following is a commonly used front-end JavaScript framework for building user interfaces?",
            options = listOf(
                Option(1, "Express.js"),
                Option(2, "Vue.js"),
                Option(3, "Angular"),
                Option(4, "Node.js")
            ),
            idCorrectOption = 2
        ),
        Question(
            id = UUID.randomUUID().toString(),
            questionText = "What is the purpose of a DNS (Domain Name System) in networking?",
            options = listOf(
                Option(1, "Encrypt data transmission"),
                Option(2, "Translate domain names to IP addresses"),
                Option(3, "Manage network security"),
                Option(4, "Control internet access")
            ),
            idCorrectOption = 2
        ),
        Question(
            id = UUID.randomUUID().toString(),
            questionText = "Which of the following is a commonly used relational database management system (RDBMS)?",
            options = listOf(
                Option(1, "MongoDB"),
                Option(2, "MySQL"),
                Option(3, "Redis"),
                Option(4, "Cassandra")
            ),
            idCorrectOption = 2
        ),
        Question(
            id = UUID.randomUUID().toString(),
            questionText = "What does the acronym 'IoT' stand for?",
            options = listOf(
                Option(1, "Internet of Technology"),
                Option(2, "Internet of Things"),
                Option(3, "Input/Output Testing"),
                Option(4, "Innovative Object Technologies")
            ),
            idCorrectOption = 2
        ),
        Question(
            id = UUID.randomUUID().toString(),
            questionText = "In web development, what does CSS3 introduce to enhance styling capabilities?",
            options = listOf(
                Option(1, "Advanced JavaScript features"),
                Option(2, "Responsive design techniques"),
                Option(3, "Extended color and layout options"),
                Option(4, "Server-side scripting support")
            ),
            idCorrectOption = 3
        ),
        Question(
            id = UUID.randomUUID().toString(),
            questionText = "What is the purpose of the 'try-catch' block in programming?",
            options = listOf(
                Option(1, "Define a function"),
                Option(2, "Handle exceptions or errors"),
                Option(3, "Create a loop"),
                Option(4, "Perform arithmetic operations")
            ),
            idCorrectOption = 2
        ),
        Question(
            id = UUID.randomUUID().toString(),
            questionText = "Which of the following is a commonly used cloud computing service model?",
            options = listOf(
                Option(1, "Platform as a Service (PaaS)"),
                Option(2, "Software as a Service (SaaS)"),
                Option(3, "Infrastructure as a Service (IaaS)"),
                Option(4, "Database as a Service (DBaaS)")
            ),
            idCorrectOption = 1
        ),
        Question(
            id = UUID.randomUUID().toString(),
            questionText = "What is the purpose of the 'scope' in programming variables?",
            options = listOf(
                Option(1, "Determine variable data type"),
                Option(2, "Define the variable's initial value"),
                Option(3, "Specify where the variable is accessible"),
                Option(4, "Control variable visibility")
            ),
            idCorrectOption = 3
        ),
        Question(
            id = UUID.randomUUID().toString(),
            questionText = "Which of the following is a commonly used programming language for statistical computing and data analysis?",
            options = listOf(
                Option(1, "Java"),
                Option(2, "C++"),
                Option(3, "Python"),
                Option(4, "Ruby")
            ),
            idCorrectOption = 3
        ),
        Question(
            id = UUID.randomUUID().toString(),
            questionText = "What is the purpose of the 'merge' function in version control systems?",
            options = listOf(
                Option(1, "Create a new branch"),
                Option(2, "Combine changes from different branches"),
                Option(3, "Delete a branch"),
                Option(4, "Undo the last commit")
            ),
            idCorrectOption = 2
        ),
        Question(
            id = UUID.randomUUID().toString(),
            questionText = "Which of the following is a commonly used front-end web development framework?",
            options = listOf(
                Option(1, "Django"),
                Option(2, "Flask"),
                Option(3, "Bootstrap"),
                Option(4, "Express.js")
            ),
            idCorrectOption = 3
        ),
        Question(
            id = UUID.randomUUID().toString(),
            questionText = "What is the purpose of the 'map' function in functional programming?",
            options = listOf(
                Option(1, "Create a new array"),
                Option(2, "Iterate over elements and transform them"),
                Option(3, "Remove elements from an array"),
                Option(4, "Sort elements in an array")
            ),
            idCorrectOption = 2
        ),
        Question(
            id = UUID.randomUUID().toString(),
            questionText = "Which of the following is a commonly used protocol for secure communication over a computer network?",
            options = listOf(
                Option(1, "HTTP"),
                Option(2, "FTP"),
                Option(3, "SSH"),
                Option(4, "SMTP")
            ),
            idCorrectOption = 3
        ),
        Question(
            id = UUID.randomUUID().toString(),
            questionText = "What is the purpose of the 'indexOf' method in string manipulation?",
            options = listOf(
                Option(1, "Convert a string to lowercase"),
                Option(2, "Find the index of a specific character or substring"),
                Option(3, "Concatenate two strings"),
                Option(4, "Remove white spaces from a string")
            ),
            idCorrectOption = 2
        )
    )
}