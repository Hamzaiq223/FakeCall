package com.tool.fakecall.Models;

import java.util.ArrayList;

public class QuestionsModel {
    public ArrayList<Character> characters;

    public ArrayList<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(ArrayList<Character> characters) {
        this.characters = characters;
    }

    public class Character{
        public String name;
        public ArrayList<QuestionsAnswer> questions_answers;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ArrayList<QuestionsAnswer> getQuestions_answers() {
            return questions_answers;
        }

        public void setQuestions_answers(ArrayList<QuestionsAnswer> questions_answers) {
            this.questions_answers = questions_answers;
        }
    }

    public class QuestionsAnswer{
        public String question;
        public String answer;

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }
    }
}
