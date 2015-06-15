#Finite Automata Optimizer

This project Finite Automata Optimizer (Otimizador de Autômatos Finitos) was developed to reduce any NFA (Nondeterministic Finite Automata) to a minimal DFA (Deterministic Finite Automata).

This is a flowchart showing the operation of the application:

![Flowchart](http://www.navossoc.com/wp-content/uploads/2013/03/fao-flowchart.png)

For the sample input below:

Input | Output
------|-------
a b c|a c
q0 q1 q2 q3|q0 q2q3q3
q0|q0
q3|q2q3q3
q0 _ q1|q0 a q2q3q3
q0 a q3|q0 c q2q3q3
q0 b q2|
q1 c q2|
q1 c q3|

Here are some screenshots of the automata been optimized:

**Initial automata**
![Initial automata](http://www.navossoc.com/wp-content/uploads/2013/03/fao-step0.png)

**Elimination of epsilon transitions**
![Elimination of epsilon transitions](http://www.navossoc.com/wp-content/uploads/2013/03/fao-step1.png)

**Elimination of nondeterminism**
![Elimination of nondeterminism](http://www.navossoc.com/wp-content/uploads/2013/03/fao-step2.png)

**Elimination of unreachable states**
![Elimination of unreachable states](http://www.navossoc.com/wp-content/uploads/2013/03/fao-step3.png)

**Elimination of useless states**
![Elimination of useless states](http://www.navossoc.com/wp-content/uploads/2013/03/fao-step4.png)

**Minimal DFA**
![Minimal DFA](http://www.navossoc.com/wp-content/uploads/2013/03/fao-step5.png)

##Source code:
This source was written in Java language using [NetBeans 7.x](https://netbeans.org/) as IDE and the [JUNG](http://jung.sourceforge.net/) library to draw the graphs.
All classes, methods, atributtes, etc. are written in English, but most comments are written in Brazilian Portuguese (as required by the teacher).

I’m not the only author of this code, it was written along with my classmate [Guilherme Maganha Moreira](https://github.com/gmmoreira).
We can not guarantee that the algorithms are 100% correct, because at the time we wrote this code we are still learning about automata.

We decided to release our source code under the [MIT License](http://opensource.org/licenses/MIT).
The JUNG library is licensed under the [BSD License](http://opensource.org/licenses/BSD-3-Clause) and is included in binary form.
