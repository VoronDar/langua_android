package com.example.langua.Reader;

import android.util.Log;

import com.example.langua.R;
import com.example.langua.cards.Card;
import com.example.langua.cards.VocabularyCard;
import com.example.langua.Databases.transportSQL.MainTransportSQL;
import com.example.langua.Databases.transportSQL.TransportSQLInterface;

import java.util.ArrayList;
import java.util.Stack;

import static com.example.langua.ApproachManager.ApproachManager.VOCABULARY_INDEX;

// получает строку, которую переводит в карты и вставляет в базы данных
class ColodeInterpier {

    private static final String importChange = "c";
    private static final String importAsk = "a";
    private static final String importUpdate = "u";
    private static final String importLeave = "l";


    /*
        правила импорта: если импортируется


     */

    static boolean readImportFile(StringBuilder allText, FileReadable activity){
        ArrayList<VocabularyCard> cards = new ArrayList<>();
        String Id = null;
        TransportSQLInterface transportSql = MainTransportSQL.getTransport(VOCABULARY_INDEX, activity.getContext());
        int availableId = transportSql.getCardCount();


        String importType = importAsk;
        Log.i("main", "startAnalyzeFile");

        try {
            Stack<Character> stack = new Stack<>();
            StringBuilder block = new StringBuilder();
            StringBuilder indicate = new StringBuilder();
            VocabularyCard card = new VocabularyCard();

            final String id = "id";
            final String word = "w";
            final String translate = "t";
            final String meaning = "m";
            final String meanNative = "mn";
            final String transcript = "tr";
            final String exampleNative = "en";
            final String example = "e";
            final String synonym = "s";
            final String antonym = "a";
            final String group = "g";
            final String part = "pa";
            final String mem = "as";
            final String train = "p";
            final String trainNative = "pn";
            final String help = "h";
            final String levelRemember = "l";
            final String levelPractice = "lp";
            final String dayTrain = "d";



            int count = 1;
            Log.i("main", allText.toString());
            for (Character n : allText.toString().toCharArray()) {
                if (stack.size() > 0 && stack.peek().equals('~')) {
                    if (n.equals('~'))
                        stack.pop();
                    continue;
                }
                switch (n) {
                    case '[':
                        if (Id == null && cards.size() == 0)
                            Id = "U";
                        else if (cards.size() == 0)
                            availableId = 1;
                        card = new VocabularyCard();
                    case '{':
                    case '<':
                        stack.add(n);
                        break;
                    case '#':
                        if (stack.size() == 0 || !stack.peek().equals('#'))
                            stack.add(n);
                        else{
                            String bl = block.toString();
                            Log.i("main", "done");
                            switch (indicate.toString()) {
                                case "":
                                    Id = block.toString();
                                    break;
                                case word:
                                    card.setWord(bl);
                                    break;
                                case translate:
                                    card.setTranslate(bl);
                                    break;
                                case transcript:
                                    card.setTranscription(bl);
                                    break;
                                case meaning:
                                    card.setMeaning(bl);
                                    break;
                                case meanNative:
                                    card.setMeaningNative(bl);
                                    break;
                                case example:
                                    card.setExample(bl);
                                    break;
                                case exampleNative:
                                    card.setExampleNative(bl);
                                    break;
                                case train:
                                    card.setTrain(bl);
                                    break;
                                case trainNative:
                                    card.setTrainNative(bl);
                                    break;
                                case group:
                                    card.setGroup(bl);
                                    break;
                                case part:
                                    card.setPart(bl);
                                    break;
                                case help:
                                    card.setHelp(bl);
                                    break;
                                case mem:
                                    card.setMem(bl);
                                    break;
                                case antonym:
                                    card.setAntonym(bl);
                                    break;
                                case synonym:
                                    card.setSynonym(bl);
                                    break;
                                case levelRemember:
                                    card.setRepeatlevel(Integer.parseInt(bl));
                                    break;
                                case levelPractice:
                                    card.setPracticeLevel(Integer.parseInt(bl));
                                    break;
                                case dayTrain:
                                    card.setRepetitionDat(Integer.parseInt(bl));
                                    break;
                                case id:
                                    card.setId(Id + bl);
                                    break;
                                default:
                                    throw new NullPointerException("Indefined index - " + indicate.toString());
                            }
                            block = new StringBuilder();
                            indicate = new StringBuilder();
                            stack.pop();
                        }
                        break;
                    case ']':
                        if (stack.peek().equals('[')) {
                            //Log.i("main", "endWord");
                            stack.pop();
                            if (card.getId() == null) {
                                card.setId(Id + availableId++);
                            }
                            cards.add(card);
                            //Log.i("mainRead", Integer.toString(count++));
                            Log.i("mainRead", card.getId());
                            card = new VocabularyCard();
                            //Log.i("main-card", "newCard");
                        } else {
                            throw new NullPointerException("[");
                        }
                        break;
                    case '>':
                        if (stack.peek().equals('<')) {
                            //Log.i("main", "endCourse");
                            stack.pop();
                        } else
                            throw new NullPointerException("couldn't find simbol '<'");
                        break;
                    case '~':
                        if (stack.size() == 0)
                            stack.add('~');
                        else
                            throw new NullPointerException("couldn't find simbol '~");
                        break;
                    case '}':
                        if (stack.peek().equals('{')) {
                            String bl = block.toString();
                            if (bl.equals(importAsk) || bl.equals(importChange) ||
                                    bl.equals(importLeave))
                                importType = block.toString();
                            block = new StringBuilder("");
                            stack.pop();
                        } else
                            throw new NullPointerException("{");
                        break;
                    case ' ':
                    case '\n':
                    case '\t':
                        if (!stack.peek().equals('#'))
                            break;
                    default:
                        if (stack.peek().equals('#') || stack.peek().equals('{'))
                            block.append(n);
                        else if (!stack.peek().equals('~'))
                            indicate.append(n);
                }
            }
        } catch (Exception e){
            Log.i("main", "emptyStack");
            activity.showError(activity.getContext().getString(R.string.read_file_error));
            return false;
        }

        push(cards, importType, transportSql);
        return true;
    }


    private static void push(ArrayList<VocabularyCard> cards, String importType, TransportSQLInterface transportSql){
        ArrayList<String> allId = transportSql.getAllCardsId();


        // А ВОТ ТУТ РАЗБЕРИСЬ И ПРОКОМЕНТИРУЙ НОРМАЛЬНО - я ничего не понимаю

        for (int i = 0; i < cards.size(); i++){

            for (String n : allId) {
                if (n.equals(cards.get(i).getId())) {

                    if (importType.equals(importLeave)){
                        cards.remove(i--);
                        break;
                    }
                    else if (importType.equals(importChange)){
                        VocabularyCard card = (VocabularyCard) transportSql.getString(n);
                        if (card.equals(cards.get(i))) {
                            cards.remove(i--);
                            Log.i("main", "leave");
                            break;
                        }
                        else
                            Log.i("main", "change");
                        if (!card.getWord().equals(cards.get(i).getWord())){
                            ArrayList<Card> allSim = transportSql.getAllCardsByWord(cards.get(i).getWord());
                            if (allSim.size() > 0){
                                //Log.i("main", "there is several words - what's to do");
                                // do dmt;
                            }
                        }
                        transportSql.deleteStringCommon(n);
                        transportSql.addString(cards.get(i));
                        //Log.i("main", "change card - " + n);
                        cards.remove(i--);
                        break;
                    }
                    else if (importType.equals(importUpdate)){
                        VocabularyCard card = (VocabularyCard) transportSql.getString(n);
                        if (card.equals(cards.get(i))) {
                            cards.remove(i--);
                            break;
                        }
                        if (!card.getWord().equals(cards.get(i).getWord())) {
                            ArrayList<Card> allSim = transportSql.getAllCardsByWord(cards.get(i).getWord());
                            if (allSim.size() > 0) {
                                //Log.i("main", "there is several words - what's to do");
                                // do dmt;
                            }
                        }
                        transportSql.deleteStringCommon(n);
                        transportSql.addString(cards.get(i));
                        cards.remove(i--);
                        //Log.i("main", "change card - " + n);
                        break;
                    }
                    else{
                        //Log.i("main", "act - ");
                    }
                }
            }
        }

        // сделать адекватный спрос

        for (int i = 0; i < cards.size(); i++){
        //    ArrayList<Card> allSim = transportSql.getAllCardsByWord(cards.get(i).getWord());
        //    if (allSim.size() > 0)
        //    {
                //Log.i("main", "new");
        //    }
        //    else
                transportSql.addString(cards.get(i));
        }



        transportSql.closeDatabases();
        Log.i("mainEnd", "END");
        // LATER

                /*
                for (int i = 0; i < cards.size(); i++){
                    for (String n : allWords) {
                        if (n.equals(cards.get(i).getWord())) {
                            Log.i("main", cards.get(i).getWord());
                        }
                    }
                }

                 */

    }

}
