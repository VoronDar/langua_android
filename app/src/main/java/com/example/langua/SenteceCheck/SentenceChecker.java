package com.example.langua.SenteceCheck;

import com.example.langua.SenteceCheck.Command.Command;
import com.example.langua.SenteceCheck.Command.MistakeCommand;
import com.example.langua.SenteceCheck.Command.OrderCommand;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.langua.SenteceCheck.Decoder.allParts;



/*
добавь адекватную проверку форм для nounable

 */



public class SentenceChecker {


    static ArrayList<Command> commands = new ArrayList<>();
    public static Part part;

    /*
    public static void main(String []args) {
        System.out.println("Hello World");

        Scanner in = new Scanner(System.in);
        while (true) {
                String mode = in.nextLine();
                if (mode.equals("all")) {
                    Tester.setIsFullResult(Boolean.parseBoolean(in.nextLine()));
                    Tester.Test(in.nextLine());
                }
                else if (mode.equals("one")) {
                    String sentence = in.nextLine();
                    String check = in.nextLine();
                    Tester.TestForOne(sentence, check);
                }
                System.out.println("\ncompleted\n\n");


        }
    }

     */

    public static boolean check(String str, String decoder){
        Tester.reset();
        part = Decoder.decoder(decoder);
        ArrayList<String> checking = prepare(str);
        for (SentenceChecker.Check check : SentenceChecker.Check.values()) {
            check.check(checking, part);
        }
        return SentenceChecker.isRight();
    }


    public static boolean isDecodable(String str){
        return (str.charAt(0) == '[');
    }

    public static String generateRightStringFromLib(String str){
        Tester.reset();
        part = Decoder.decoder(str);
        return getRightString();
    }

    // запускается в самом конце - поэтому все элементы уже имеют watched = 2
    public static String getRightString(){
        ArrayList<String> sentence = new ArrayList<>();
        Part now = part;
        while (now != null){
            switch (now.getClass().getSimpleName()){
                case "Subject":
                    Subject subject = (Subject)now;
                    if (getReady(now)){
                        now.watched = 4;
                        if (subject.getArticle() != null)
                            sentence.add(subject.getArticle().name);
                        if (subject.getAdjective() != null) {
                            if (subject.getAdjective().getArticle() != null)
                                sentence.add(subject.getAdjective().getArticle().name);
                            sentence.add(subject.getAdjective().name);
                        }
                        sentence.add(subject.name);
                    }
                    if (getAvailable(subject.getPredicate()))
                        now = subject.getPredicate();
                    else
                        now = now.back;
                    break;
                case "PredicateNoun":
                    PredicateNoun predicateNoun = (PredicateNoun)now;
                    if (predicateNoun.getModalVerb() != null)
                        sentence.add(predicateNoun.getModalVerb().name);
                    if (predicateNoun.getArticle() != null)
                        sentence.add(predicateNoun.getArticle().name);
                    if (predicateNoun.getAdjective() != null) {
                        if (predicateNoun.getAdjective().getArticle() != null)
                            sentence.add(predicateNoun.getAdjective().getArticle().name);
                        sentence.add(predicateNoun.getAdjective().name);
                    }
                    sentence.add(predicateNoun.name);
                    now = now.back;
                    break;
                case "AddictionNoun":
                    AddictionNoun addictionNoun = (AddictionNoun)now;
                    if (addictionNoun.getArticle() != null)
                        sentence.add(addictionNoun.getArticle().name);
                    if (addictionNoun.getAdjective() != null){
                        if (addictionNoun.getAdjective().getArticle() != null)
                            sentence.add(addictionNoun.getAdjective().getArticle().name);
                        sentence.add(addictionNoun.getAdjective().name);
                    }
                    sentence.add(addictionNoun.name);
                    now = now.back;
                    break;
                case "PredicateVerb":
                    PredicateVerb predicateVerb = (PredicateVerb)now;
                    if (getReady(now)) {
                        now.watched = 4;
                        if (predicateVerb.getModalVerb() != null)
                            sentence.add(predicateVerb.getModalVerb().name);
                        if (predicateVerb.getSetting() != null && predicateVerb.getSetting().getSettingType() == SettingType.frequency) {
                            if (predicateVerb.getSetting().getPreposition() != null)
                                sentence.add(predicateVerb.getSetting().getPreposition().name);
                            if (predicateVerb.getSetting().getArticle() != null)
                                sentence.add(predicateVerb.getSetting().getArticle().name);
                            sentence.add(predicateVerb.getSetting().name);
                        }
                        sentence.add(predicateVerb.name);
                    }

                    if (getAvailable(predicateVerb.getAddiction())) {
                        now = predicateVerb.getAddiction();
                    } else
                    if (getAvailable(predicateVerb.getSetting())) {
                        now = predicateVerb.getSetting();
                    }else
                        now = now.back;
                    break;
                case "AddictionVerb":
                    AddictionVerb addictionVerb = (AddictionVerb)now;
                    if (getReady(now)) {
                        now.watched = 4;
                        if (getAvailable(addictionVerb.getArticle()))
                            sentence.add(addictionVerb.getArticle().name);
                        if (addictionVerb.getSetting() != null && addictionVerb.getSetting().getSettingType() == SettingType.frequency) {
                            if (addictionVerb.getSetting().getPreposition() != null)
                                sentence.add(addictionVerb.getSetting().getPreposition().name);
                            if (addictionVerb.getSetting().getArticle() != null)
                                sentence.add(addictionVerb.getSetting().getArticle().name);
                            sentence.add(addictionVerb.getSetting().name);
                        }
                        sentence.add(addictionVerb.name);

                    }

                    if (getAvailable(addictionVerb.getAddiction())) {
                        now = addictionVerb.getAddiction();
                    } else if (getAvailable(addictionVerb.getSetting())) {
                        now = addictionVerb.getSetting();
                    } else
                        now = now.back;
                    break;
                case "Setting":
                case "SettingNoun":
                    Setting settingNoun = (Setting)now;
                    if (settingNoun.getPreposition() != null)
                        sentence.add(settingNoun.getPreposition().name);
                    if (settingNoun.getArticle() != null)
                        sentence.add(settingNoun.getArticle().name);
                    sentence.add(settingNoun.name);
                    now = now.back;
                    break;
                case "SettingVerb":
                    SettingVerb settingVerb = (SettingVerb)now;
                    if (getReady(now)) {
                        now.watched = 4;
                        if (settingVerb.getPreposition() != null)
                            sentence.add(settingVerb.getPreposition().name);
                        if (settingVerb.getArticle() != null)
                            sentence.add(settingVerb.getArticle().name);
                        sentence.add(settingVerb.name);
                        if (settingVerb.getUnion() != null)
                            sentence.add(settingVerb.getUnion().name);
                    }
                    if (getAvailable(settingVerb.getAddiction()))
                        now = settingVerb.getAddiction();
                    else if (getAvailable(settingVerb.getSubject()))
                        now = settingVerb.getSubject();
                    else if (getAvailable(settingVerb.getSetting()))
                        now = settingVerb.getSetting();
                    else
                        now = now.back;
                    break;

            }
            if (now != null && now.watched < 3)
                now.watched = 3;
        }
        StringBuilder builder = new StringBuilder();
        for (String s: sentence) {
            builder.append(s + " ");
        }
        return builder.toString();
    }

    private static boolean getAvailable(Part part){
        return part != null && part.watched < 3;
    }

    private static boolean getReady(Part part){
        return part != null && part.watched < 4;
    }

    private static void putCommand(Command command){
        commands.add(command);
    }

    // важно - после показа ошибок их закрывают
    public static String showCommand(){
        StringBuilder builder = new StringBuilder();
        for (Command a: commands) {
            a.show();
            builder.append(a.name + "\n");
        }
        return builder.toString();
    }

    public static boolean isRight(){
        return (commands.size()==0);
    }



    static ArrayList<String> prepare(String checking){

        String check = checking.substring(1).toLowerCase();

        ArrayList<String> strings = new ArrayList<>();
        int last = 0;
        for (int i = 0; i < check.length() - 1; i++){
            if (check.charAt(i) == ' '){
                strings.add(check.substring(last, i));
                last = i+1;
            }
        }
        strings.add(checking.substring(last));
        return strings;
    }

    static HashMap<Integer, Part> id;
    static ArrayList<Part> unfounded_id;
    static ArrayList<Integer> empty_id;
    public enum Check{
        find{
            @Override
            void check(ArrayList<String> string, Part sentence) {
                //Part now = sentence;
                id = new HashMap(string.size());
                unfounded_id = new ArrayList<>();
                empty_id = new ArrayList<>();

                for (Part now: allParts){
                    if (!id.containsKey(string.indexOf(now.name)))
                        now.id = string.indexOf(now.name);
                    else
                        now.id = string.lastIndexOf(now.name);
                    if (now.id != -1) {
                        id.put(now.id, now);
                        now.watched = 1;
                    }
                    else {
                        unfounded_id.add(now);
                    }
                }
            }
            },

            checkMistakes {
                @Override
                void check(ArrayList<String> string, Part sentence) {
                    ArrayList<Part> other_unfounded_id = unfounded_id;
                    unfounded_id = new ArrayList<>();
                    for (Part now: other_unfounded_id){
                        switch (now.getClass().getSimpleName()){
                            case "Subject":
                            case "PredicateNoun":
                            case "AddictionNoun":
                                checkNounable(now, string);
                                break;
                            case "SettingVerb":
                            case "PredicateVerb":
                            case "AddictionVerb":
                                checkVerbable(now, string);
                                break;
                            case "SettingNoun":
                            case "Setting":
                            case "Adjective":
                                    for (int i = 0; i < string.size(); i++) {
                                        if (id.containsKey(i)) continue;
                                        if (checkUnion(string.get(i), now, i)) {
                                            now.id = i;
                                            id.put(now.id, now);
                                            break;
                                        }
                                    }
                                    if (now.id == -1) {
                                        putCommand(new Command(now.name + " wasn't found!"));
                                        unfounded_id.add(now);
                                    }
                                break;
                            case "Preposition":
                                checkPreposition((Preposition) now, string);
                                break;
                            case "ModalVerb":
                            case "Article":
                                checkServicePart(now, string);
                        }
                        now.watched = 1;
                    }

                    // если слов больше, чем нашлось
                    ArrayList<String> template = (ArrayList<String>) string.clone();
                    empty_id.clear();
                    if (id.size() < string.size()){
                        for (int i = 0; i < template.size(); i++){
                            if (!id.containsKey(i)){
                                putCommand(new Command("unknown sequence was found " + template.get(i)));
                                empty_id.add(i);
                            }
                        }
                    }
                    if (unfounded_id.size() == 1 && empty_id.size() == 1){
                        System.out.println("let '" + template.get(empty_id.get(0)) + "' be '" + unfounded_id.get(0).name);
                        id.put(empty_id.get(0), unfounded_id.get(0));
                        empty_id.clear();
                        unfounded_id.clear();
                    }
                }

            private boolean isSynonym(String checking, Part part) {
                    /*
                    if (part.isException)
                        ArrayList<String> synonyms = transportSql.getCardSynonymsFromException(part.name);
                    else if (getVerbForm(part.name) == VerbForm.infinite) {
                        ArrayList<String> synonyms = transportSql.getCardSynonyms(part.name);
                    } else{
                        ArrayList<String> synonyms = tryCastToInfAndFindSynonyms(part.name));
                    }

                     */

                    if (part.type == Type.verb)
                        System.out.println(tryCastToInfAndFindSynonyms(part.name));


                ArrayList<String> synonyms = new ArrayList<>();
                //if (part.type == Type.verb)
                //    synonyms.add("hug");
                if (synonyms.size() == 0) return false;
                for (String s: synonyms) {
                    if (part.type == Type.verb) {
                        if (s.equals(checking) && getVerbForm(part.name) == VerbForm.infinite) return true;
                        String prepared = verbFormBuilding(s, getVerbForm(part.name));
                        if (prepared.equals(checking)) return true;
                            if (verbFormsInaccordance(prepared, checking, false)) {
                                putCommand(new MistakeCommand(checking + " (" + prepared + ") verb forms incompatible", part.id, prepared));
                                return true;
                        }
                    }
                    if (part.type == Type.noun) {
                        /*
                        if (s.equals(checking) && ((Nounable)part).getNounForm() == NounForm.simple) return true;
                        if (nounIncompatible(s,checking, ((Nounable)part).getNounForm())) {
                            putCommand(new MistakeCommand(checking + " (" + part.name + ") noun forms incompatible", part.id, s));
                            return true;
                        }

                         */
                    }
                    if (mistakeCheck(s, checking)) {
                        putCommand(new MistakeCommand(checking + " - " + s + ": mistake", part.id, s));
                        return true;
                    }
                }
                return false;
            }


                //////// ПРОВЕРКА ГЛАГОЛЬНЫХ ФОРМ //////////////////////////////////////////////////////////////////////////

            // количество глассных в слове
            int getVowelCount(String word){
                Pattern pattern = Pattern.compile("[aoyeiu]");
                Matcher matcher = pattern.matcher(word);
                int count = 0;
                while (matcher.find())
                    count++;
                return count;
            }

            // возвращает true, если y последняя в слове и перед ней есть согласная
            boolean getLastYCanErased(String word){
                if (word.charAt(word.length()-1) != 'y') return false;
                Pattern pattern = Pattern.compile("[aoyeiu]");
                Matcher matcher = pattern.matcher(word.substring(word.length()-2, word.length()-1));
                return !matcher.matches();
            }

            // возвращает true, если последняя буква в глаголе может быть вставлена 2 раза (исключения не здесь)
            boolean getLastWordCanMultiply(String word){
                Pattern pattern = Pattern.compile("[^aoyiue]");
                Matcher matcher = pattern.matcher(word.substring(word.length()-1));

                if (!matcher.find()) return false;
                matcher.reset(word.substring(word.length()-2, word.length()-1));
                if (!matcher.find()) {
                    if (getVowelCount(word) == 1) return true;

                    char firstVowel = '!';
                    for (int i = word.length()-2; i >= 0; i--){
                        matcher.reset(word.substring(i, i+1));
                        if (!matcher.find()){
                            if (firstVowel == '!') firstVowel = word.charAt(i);
                            else return (firstVowel != word.charAt(i));
                        }
                    }
                    //String transcript = transportSql.getTranscription();
                    String transcript = "[fəˈɡet]";
                    if (transcript == null) {
                        System.out.println("there is no transcription - 'ing' form writing might be incorrect");
                        return false;
                    }
                    // работаем так - ждем 2 согласного - если там апостроф был ровно до него - удвоить

                    boolean isVowel = false;
                    Pattern consonant = Pattern.compile("[bdf3ɡhklmnpstvz∫rθðŋw\\[\\]]");
                    Matcher matcherConsonant = consonant.matcher(transcript.substring(transcript.length()-2));
                    for (int i = transcript.length()-2; i >= 0; i--){
                        matcherConsonant.reset(transcript.substring(i, i+1));
                        if (!matcherConsonant.matches()) {
                            isVowel = true;
                        }
                        if (matcherConsonant.matches() && isVowel) {
                            return (transcript.charAt(i - 1) == '\'' || transcript.charAt(i-1) == 'ˈ');
                        }
                    }

                }
                return false;
            }


                // когда эта функция запускается, строка должна быть не исключением
                // пытается сформировать инфинитив (находит совпадение в базе данных, ибо без этого точно хрен что определишь)
                // и найти синонимы
                // возвращает null, если не удалось, или нет синонимов
                private ArrayList<String> tryCastToInfAndFindSynonyms(String name) {
                    VerbForm verbForm = getVerbForm(name);
                    String cast = "";
                    ArrayList<String> synonyms = null;
                    switch (verbForm){
                        case past:
                            cast = name.substring(0, name.indexOf("ed"));
                            break;
                        case simple:
                            cast = name.substring(0, name.length()-1);
                            break;
                        case ing:
                            cast = name.substring(0, name.indexOf("ing"));
                            break;
                    }
                    System.out.println(cast);
                    //synonyms = transportSql.getCardSynonyms(cast);
                    if (synonyms == null){
                        switch (verbForm){
                            case past:
                                cast = name.substring(0, name.length()-1);
                                break;
                            case simple:
                                if (name.contains("es"))
                                    cast = name.substring(0,  name.indexOf("es"));
                                break;
                            case ing:
                                cast = name.substring(0, name.indexOf("ing")) + "e";
                                break;
                        }
                        System.out.println(cast);
                        //synonyms = transportSql.getCardSynonyms(cast);
                        if (synonyms == null){
                            switch (verbForm){
                                case past:
                                    if (name.contains("ied"))
                                        cast = name.substring(0, name.indexOf("ied")) + "y";
                                    else if (name.contains("icked"))
                                        cast = name.substring(0, name.indexOf("ked"));
                                    else if (name.charAt(name.indexOf("ed") -1) == name.charAt(name.indexOf("ed") -2))
                                        cast = name.substring(0, name.indexOf("ed") - 1);
                                    else
                                        return null;
                                    break;
                                case simple:
                                    if (name.contains("ies"))
                                        cast = name.substring(0,  name.indexOf("ies")) + "y";
                                    else
                                        return null;
                                    break;
                                case ing:
                                    if (name.contains("ying"))
                                        cast = name.substring(0, name.indexOf("ying")) + "ie";
                                    else if (name.contains("icking"))
                                        cast = name.substring(0, name.indexOf("king"));
                                    else if (name.charAt(name.indexOf("ing") -1) == name.charAt(name.indexOf("ing") -2))
                                        cast = name.substring(0, name.indexOf("ing") - 1);
                                    else return null;
                                    break;
                            }
                            System.out.println(cast);
                            //synonyms = transportSql.getCardSynonyms(cast);
                        }
                    }
                    return synonyms;
                }



                // строит форму глагола (оно не разбирается в исключениях)
            private String verbFormBuilding(String verb, VerbForm verbForm){

                final String endingS = verb.substring(verb.length()-1);
                final String endingSS = verb.substring(verb.length()-2);
                final String fundament = verb.substring(0, verb.length() - 2);
                final String fundament1 = verb.substring(0, verb.length() - 1);
                final String endingSSS;
                if (verb.length() > 2)
                    endingSSS = verb.substring(verb.length()-3);
                else
                    endingSSS = null;
                switch (verbForm){
                    case infinite:
                        return verb;
                    case ing:
                        if (!endingSS.equals("e") && endingS.equals("e"))
                            return fundament1 + "ing";
                        if (endingSS.equals("ie"))
                            return fundament + "ying";
                        else if (endingSS.equals("ic"))
                            return fundament + "icking";
                            //else if (Ruler.getDialect() == Dialect.Br && verb.substring(verb.length()-1).equals("l"))
                        else if (endingS.equals("l")){
                            if (!endingSS.equals("ll"))
                                return verb + "ling";
                            else
                                return verb + "ing";
                        }
                        else if (endingS.equals("w") || endingS.equals("x"))
                            return verb + "ing";
                        else if (getLastWordCanMultiply(verb))
                            return verb + endingS + "ing";
                        else
                            return verb + "ing";
                    case simple:
                        switch (endingS){
                            case "s":
                            case "x":
                            case "o":
                                return verb + "es";
                        }
                        switch (endingSS){
                            case "zz":
                            case "sh":
                            case "ch":
                                return verb + "es";
                        }
                        if ("tch".equals(endingSSS)) {
                            return verb + "es";
                        }
                        if (getLastYCanErased(verb))
                            return verb.substring(0, verb.length()-1) + "ies";
                        return verb + "s";
                    case past:
                        if ("e".equals(endingS))
                            return verb + "d";
                        if (getLastYCanErased(verb))
                            return verb.substring(0, verb.length()-1) + "ied";
                        if (endingSS.equals("ic"))
                            return fundament + "icked";
                        else if (endingS.equals("l")) {
                            if (!endingSS.equals("ll"))
                                return verb + "led";
                            else
                                return verb + "ed";
                        }
                        else if (endingS.equals("w") || endingS.equals("x"))
                            return verb + "ed";
                        else if (getLastWordCanMultiply(verb))
                            return verb + endingS + "ed";
                        else
                            return verb + "ed";
                }
                return null;
            }



            // находит или ошибку в использовании формы или ошибку (далеко не каждую) в образовании формы (только для глаголов)
            /*
            для неправильных глаголов - создать базу данных с исключениями и смотреть по ней
            важно - если это исключение, то нужно его как-то пометить, ибо бывают случаи, когда исключения не отделимы от адекватных слов
            к примеру - found (может быть формой find а может быть просто found инфинитивом)

             */

            private VerbForm getVerbForm(String name){
                VerbForm verbForm;
                if (name.length() > 2 && name.substring(name.length()-2).equals("ed"))
                    verbForm = VerbForm.past;
                else if (name.length() > 3  && name.substring(name.length()-3).equals("ing"))
                    verbForm = VerbForm.ing;
                else if (name.length() > 2 && (name.substring(name.length()-1).equals("s")
                        && name.charAt(name.length()-2) != 's' && name.charAt(name.length()-2) != 'o'
                        && name.charAt(name.length()-2) != 'x'
                        && !name.substring(name.length()-3, name.length()-1).equals("ch")
                        && !name.substring(name.length()-3, name.length()-1).equals("zz")
                        && !name.substring(name.length()-3, name.length()-1).equals("sh")))
                    verbForm = VerbForm.simple;
                else
                    verbForm = VerbForm.infinite;
                return verbForm;
            }

                private NounForm getNounForm(String name){
                    NounForm nounForm;
                    if (name.length() > 2 && (name.substring(name.length()-2).equals("'s") || name.substring(name.length()-2).equals("`s")))
                        nounForm = NounForm.attachment;
                    else
                        nounForm = NounForm.simple;
                    return nounForm;
                }

            private boolean verbFormsInaccordance(String name, String s, boolean isException) {
                if (isException){
                    /*
                    важно помнить, что оно должно найти остальные варианты по одному из исключений - соответственно оно не будет ключевым
                    и нужно будет просмотреть вообще все исключения, чтобы найти необходимый вариант
                     */
                    //ArrayList<String> varies = transportSql.getExceptionsVaries(name);
                    ArrayList<String> varies = new ArrayList<>();
                    varies.add("begin");
                    varies.add("begun");
                    varies.add("began");

                    for (String v: varies){
                        if (s.equals(v)) return true;
                    }
                }
                else{
                    VerbForm verbForm = getVerbForm(name);
                        switch (verbForm){
                            case infinite:
                                if (s.startsWith(name)) return true;
                                if ((name.charAt(name.length()-1) == 'y' || name.charAt(name.length()-1) == 'e')
                                        && s.startsWith(name.substring(0,name.length()-1)))
                                    return true;
                                break;
                            case ing:
                                if (s.startsWith(name.substring(0,name.length()-4))) return true;
                                break;
                            case simple:
                                if (s.startsWith(name.substring(0,name.length()-2))) return true;
                            case past:
                                if (s.startsWith(name.substring(0,name.length()-3))) return true;

                        }

                }
                return false;
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////

            private boolean nounIncompatible(String name, String s, boolean isException){
                if (isException){
                    /*
                    важно помнить, что оно должно найти остальные варианты по одному из исключений - соответственно оно не будет ключевым
                    и нужно будет просмотреть вообще все исключения, чтобы найти необходимый вариант
                     */
                    //ArrayList<String> varies = transportSql.getExceptionsVaries(name);
                    ArrayList<String> varies = new ArrayList<>();
                    varies.add("child");
                    varies.add("children");
                    varies.add("child's");

                    for (String v: varies){
                        if (s.equals(v)) return true;
                    }
                }
                NounForm nounForm = getNounForm(name);
                switch (nounForm) {
                    case simple:
                        if (s.startsWith(name)) return true;
                        break;
                    case multiple:
                        if (name.charAt(name.length()-2) == 'e')
                            return s.startsWith(name.substring(0, name.length()-2));
                        else
                            return s.startsWith(name.substring(0, name.length()-1));
                    case attachment:
                        return s.startsWith(name.substring(0, name.length()-2));
                }
                return false;
            }

            boolean checkUnion(String checking, Part part, int now_id){

                if (isSynonym(checking ,part)){
                    part.id = now_id;
                    id.put(part.id, part);
                    return true;
                }

                if (mistakeCheck(part.name, checking)){
                    putCommand(new MistakeCommand("mistake -  (" + checking + ")- " + part.name, part.id, part.name));
                    part.id = now_id;
                    id.put(part.id, part);
                    return true;
                }
                return false;
            }

            void checkNounable(Part now, ArrayList<String> string) {
                if (now.id == -1) {
                    for (int i = 0; i < string.size() ; i++) {
                        if (id.containsKey(i)) continue;
                        if (nounIncompatible(now.name, string.get(i), now.isException)){
                            putCommand(new MistakeCommand("noun forms incompatible (" + string.get(i) + ")- " + now.name, now.id, now.name));
                            now.id = i;
                            id.put(now.id, now);
                            break;
                        }
                        if (checkUnion(string.get(i), now, i))
                            break;
                    }
                    if (now.id == -1) {
                        putCommand(new Command(now.name + " wasn't found!"));
                        unfounded_id.add(now);
                    }
                }
            }

            void checkVerbable(Part now, ArrayList<String> string) {

                if (now.id == -1) {
                    for (int i = 0; i < string.size(); i++) {
                        if (id.containsKey(i)) continue;
                        if (verbFormsInaccordance(now.name, string.get(i), now.isException)) {
                            putCommand(new MistakeCommand("verb forms incompatible (" + string.get(i) + ")- " + now.name, now.id, now.name));
                            now.id = i;
                            id.put(now.id, now);
                            break;
                        }
                        if (checkUnion(string.get(i), now, i))
                            break;

                    }
                    if (now.id == -1) {
                        putCommand(new Command(now.name + " wasn't found!"));
                        unfounded_id.add(now);
                    }
                }
            }

            // проверяет артикли и модальные/служебные глаголы
            void checkServicePart(Part article, ArrayList<String> string) {
                if (article != null) {
                    if (article.id == -1 && article.watched == 0) {
                        cycle:
                        for (int i = 0; i < string.size(); i++) {
                            if (id.containsKey(i)) continue;
                                if (article.name.length() > 2) {
                                    if (mistakeCheck(string.get(i), article.name)) {

                                        putCommand(new MistakeCommand("mistake -  (" + string.get(i) + ")- " + article.name, article.id, article.name));
                                        article.id = i;
                                        id.put(article.id, article);
                                        break;
                                    }

                                    switch (string.get(i)){
                                        case "this":
                                            if (article.name.equals("the")){
                                                article.id = i;
                                                id.put(article.id, article);
                                                break cycle;
                                            } break;
                                        case "the":
                                            if (article.name.equals("this")){
                                                article.id = i;
                                                id.put(article.id, article);
                                                break cycle;
                                            } break;
                                        case "a":
                                            if (article.name.equals("an")){
                                                putCommand(new Command("'an' должно быть использовано вместо 'a'"));
                                                article.id = i;
                                                id.put(article.id, article);
                                                break cycle;
                                            } break;
                                        case "an":
                                            if (article.name.equals("a")){
                                                putCommand(new Command("'a' должно быть использовано вместо 'an'"));
                                                article.id = i;
                                                id.put(article.id, article);
                                                break cycle;
                                            } break;

                                    }
                                }
                            }
                        }
                    if (article.id == -1 && article.watched == 0) {
                        putCommand(new Command(article.name + " wasn't found!"));
                        unfounded_id.add(article);
                    }
                    article.watched = 1;
                }
        }

            private void checkPreposition(Preposition preposition, ArrayList<String> string){
                String[] prepositions = {""};

                if (preposition != null) {
                    if (preposition.id == -1 && preposition.watched == 0) {
                        for (int i = 0; i < string.size(); i++) {
                            if (id.containsKey(i)) continue;
                            if (preposition.name.length() > 4) {
                                if (mistakeCheck(string.get(i), preposition.name)) {
                                    putCommand(new MistakeCommand("mistake -  (" + string.get(i) + ")- " + preposition.name, preposition.id, preposition.name));
                                    preposition.id = i;
                                    id.put(preposition.id, preposition);
                                    break;
                                }
                            }
                        }
                    }
                    if (preposition.id == -1 && preposition.watched == 0) {
                        preposition.watched = 1;
                        unfounded_id.add(preposition);

                        for (int i = 0; i < string.size(); i++) {
                            if (id.containsKey(i)) continue;
                            if (prepositionJumbled(preposition, string.get(i), "in", "into")){
                                // ok
                            } else if (prepositionJumbled(preposition, string.get(i), "with", "by") ||
                                    prepositionJumbled(preposition, string.get(i), "beside", "besides")||
                                    prepositionJumbled(preposition, string.get(i), "of", "off"))
                                putCommand(new Command(string.get(i) + " have to be replaced by a '" + preposition.name + "'"));
                            else{
                                putCommand(new Command(preposition.name + " wasn't found!"));
                                unfounded_id.add(preposition);
                                return;
                            }
                            preposition.id = i;
                            id.put(preposition.id, preposition);
                            break;
                        }


                    }
                }
            }

            boolean prepositionJumbled(Part preposition, String check, String firstPreposition, String lastPreposition){
                return (preposition.name.equals(firstPreposition) && check.equals(lastPreposition)) ||
                        (preposition.name.equals(lastPreposition) && check.equals(firstPreposition));
            }

            boolean mistakeCheck(String name, String cheking){

                // я хз как это правильно проверять, но буду проверять на замену менее 20% букв, пропуск 1 буквы или вставку одной лишней

                if (name.length() == cheking.length()) {
                    ArrayList<Integer> compared = new ArrayList<>(1);
                    for (int i = 0; i < name.length(); i++) {
                        if (cheking.charAt(i) != name.charAt(i))
                            compared.add(i);
                    }
                    return compared.size() <= 2;
                } else if (name.length() < cheking.length()) {
                    ArrayList<Integer> compared = new ArrayList<>(1);
                    int minus = 0;
                    for (int i = 0; i < cheking.length(); i++) {
                        if (i < name.length()) {
                            if (cheking.charAt(i) != name.charAt(i - minus)) {
                                compared.add(i);
                                minus++;
                            }
                        }
                    }
                    return (compared.size() <= 2 && compared.size() < name.length()/3.3);
                } else{
                    ArrayList<Integer> compared = new ArrayList<>(1);
                    int minus = 0;
                    for (int i = 0; i < name.length(); i++) {
                        if (i < cheking.length()) {
                            if (cheking.charAt(i - minus) != name.charAt(i)) {
                                compared.add(i);
                                minus++;
                            }
                        }
                    }
                    return (compared.size() <= 2 && compared.size() < cheking.length()/3.3);
                }
            }

        },
        order {
            Part now;
            @Override
            void check(ArrayList<String> string, Part sentence) {
                now = sentence;

                while (now != null) {
                    switch (now.getClass().getSimpleName()) {
                        case "Subject":
                            Subject subject = (Subject) now;
                            if (checkForAdjective(subject) && checkForActive(subject.getPredicate())) {
                                if (checkForNotNear(subject, subject.getPredicate(), false, true))
                                    putCommand(new OrderCommand(subject.getPredicate().name + " must be after " + subject.name));
                                now = subject.getPredicate();
                            } else {

                                if (checkForActive(subject.getArticle())) {
                                    if (checkForNotNear(subject.getArticle(), subject, false, true))
                                        putCommand(new OrderCommand(subject.name + " must be after " + subject.getArticle().name));
                                }
                                now = now.back;
                            }
                            break;
                        case "PredicateNoun":
                            PredicateNoun predicateNoun = (PredicateNoun) now;

                            if (checkForAdjective(predicateNoun)) {

                                if (checkForActive(predicateNoun.getArticle())) {
                                    if (checkForNotNear(predicateNoun.getArticle(), predicateNoun, false, true)) {
                                        putCommand(new OrderCommand(predicateNoun.name + " must be after " + predicateNoun.getArticle().name));
                                    }
                                    if (checkForActive(predicateNoun.getModalVerb())) {
                                        if (checkForNotNear(predicateNoun.getModalVerb(), predicateNoun.getArticle(), false, false)) {
                                            putCommand(new OrderCommand(predicateNoun.getModalVerb().name + " must be before " + predicateNoun.getArticle().name));
                                        }
                                    }
                                } else if (checkForActive(predicateNoun.getModalVerb())) {
                                    if (checkForNotNear(predicateNoun.getModalVerb(), predicateNoun, false, true)) {
                                        putCommand(new OrderCommand(predicateNoun.getModalVerb().name + " must be before " + predicateNoun.name));
                                    }
                                }
                                now = now.back;
                            }
                            break;
                        case "PredicateVerb":
                            PredicateVerb predicate = (PredicateVerb) now;

                            if (checkForPresence(predicate.getSetting()) && predicate.getSetting().getSettingType() == SettingType.time) {
                                if ((checkForNotNear(predicate, predicate.getSetting(), true, true)) &&
                                        (checkForNotNear(predicate.getSetting(), getClosestSubject(predicate), true, true))) {
                                    putCommand(new OrderCommand(predicate.getSetting().name + " must be after " + getClosestSubject(predicate).name));
                                }
                                now = predicate.getSetting();
                            } else if (checkForPresence(predicate.getSetting()) && predicate.getSetting().getSettingType() == SettingType.frequency) {
                                if (checkForActive(predicate.getModalVerb())) {
                                    if (checkForNotNear(predicate.getModalVerb(), predicate.getSetting(), false, true))
                                        putCommand(new OrderCommand(predicate.getSetting().name + " must be after " + predicate.getModalVerb().name));
                                } else if (checkForNotNear(predicate.getSetting(), predicate, true, true))
                                    putCommand(new OrderCommand(predicate.getSetting().name + " must be before " + predicate.name));

                                now = predicate.getSetting();
                            } else if (checkForPresence(predicate.getSetting())) {
                                if (checkForPresence(predicate.getAddiction())) {
                                    if (checkForNotNear(predicate.getAddiction(), predicate.getSetting(), true, true))
                                        putCommand(new OrderCommand(predicate.getSetting().name + " must be after " + predicate.getAddiction().name));
                                } else {
                                    if (checkForNotNear(predicate, predicate.getSetting(), false, true))
                                        putCommand(new OrderCommand(predicate.getSetting().name + " must be after " + predicate.name));
                                }
                                now = predicate.getSetting();
                            } else if (checkForPresence(predicate.getAddiction())) {
                                if (checkForNotNear(predicate, predicate.getAddiction(), false, true)) {
                                    putCommand(new OrderCommand(predicate.getAddiction().name + " must be after " + predicate.name));
                                }
                                now = predicate.getAddiction();
                            } else {
                                if (checkForActive(predicate.getModalVerb())) {
                                    if (checkForNotNear(predicate.getModalVerb(), predicate, false, true)) {
                                        putCommand(new OrderCommand(predicate.getModalVerb().name + " must be before " + predicate.name));
                                    }
                                }
                                now = now.back;
                            }
                            break;
                        case "SettingNoun":
                            if (!checkForAdjective((Nounable) now)) break;
                        case "Setting":
                            if (checkForActive(((Setting) now).getPreposition())) {
                                if (checkForActive(((Setting) now).getArticle())) {
                                    if (checkForNotNear(((Setting) now).getPreposition(), ((Setting) now).getArticle(), false, true)) {
                                        putCommand(new OrderCommand(((Setting) now).getArticle().name + " must be after " + ((Setting) now).getPreposition().name));
                                    }
                                } else {
                                    if (checkForNotNear(((Setting) now).getPreposition(), now, false, true)) {
                                        putCommand(new OrderCommand(now.name + " must be after " + ((Setting) now).getPreposition().name));
                                    }
                                }
                            }
                        case "Adjective":
                            ArticleHolder holder = (ArticleHolder) now;
                            if (checkForActive(holder.getArticle())) {
                                if (checkForNotNear(holder.getArticle(), now, false, true)) {
                                    putCommand(new OrderCommand(now.name + " must be after " + holder.getArticle().name));
                                }
                            }
                            now = now.back;
                            break;
                        case "AddictionNoun":

                            if (checkForAdjective((Nounable) now)) {
                                if (checkForActive(((ArticleHolder) now).getArticle())) {
                                    if (checkForNotNear(((ArticleHolder) now).getArticle(), now, false, true)) {
                                        putCommand(new OrderCommand(now.name + " must be after " + ((ArticleHolder) now).getArticle().name));
                                    }
                                }
                                now = now.back;
                            }
                            break;
                        case "SettingVerb":
                            SettingVerb settingVerb = (SettingVerb) now;
                            if (checkForPresence(settingVerb.getSubject())) {
                                if (checkForActive(settingVerb.getUnion())) {
                                    if (checkForNotNear(settingVerb.getUnion(), settingVerb.getSubject(), true, true))
                                        putCommand(new OrderCommand(settingVerb.getSubject().name + " must be after " + settingVerb.getUnion().name));

                                    if (checkForNotNear(settingVerb, settingVerb.getUnion(), true, true))
                                        putCommand(new OrderCommand(settingVerb.getUnion().name + " must be after " + settingVerb.name));
                                } else {
                                    if (checkForNotNear(settingVerb, settingVerb.getSubject(), true, true))
                                        putCommand(new OrderCommand(settingVerb.getSubject().name + " must be after " + settingVerb.name));
                                }
                                now = settingVerb.getSubject();
                                break;
                            }
                        case "AddictionVerb":
                            Verbable verbable = (Verbable) now;
                            ArticleHolder Articleholder = (ArticleHolder) now;


                            if (checkForPresence(verbable.getSetting())) {
                                if (checkForPresence(verbable.getAddiction())) {
                                    if (checkForNotNear(verbable.getAddiction(), verbable.getSetting(), true, true))
                                        putCommand(new OrderCommand(verbable.getSetting().name + " must be after " + verbable.getAddiction().name));
                                } else {
                                    if (checkForNotNear(now, verbable.getSetting(), false, true))
                                        putCommand(new OrderCommand(verbable.getSetting().name + " must be after " + now.name));
                                }
                                now = verbable.getSetting();
                            } else if (checkForPresence(verbable.getAddiction())) {
                                if (checkForNotNear(now, verbable.getAddiction(), false, true)) {
                                    putCommand(new OrderCommand(verbable.getAddiction().name + " must be after " + now.name));
                                }
                                now = verbable.getAddiction();
                            } else {
                                if (checkForActive(Articleholder.getArticle())) {
                                    if (checkForNotNear(Articleholder.getArticle(), now, false, true)) {
                                        putCommand(new OrderCommand(now.name + " must be after " + Articleholder.getArticle().name));
                                    }
                                }
                                now = now.back;
                            }
                            break;
                    }
                    if (now != null)
                        now.watched = 2;
                }
            }

            boolean checkForPresence(Part part){
                return part != null && part.watched < 2;
            }
            boolean checkForActive(Part part){
                return checkForPresence(part) && part.id != -1;
            }

            // проверяет - если между двумя айдишниками пустые айдишники (айдишники, которые не учитываются из-за того,
            // что вообще хрен знает откуда взялись)
            // возвращает true - если они идут один за другим и false - если между ними что-то есть или они идут не один за другим
            // важно - первое число должно быть больше второго, иначе false
            boolean checkForNotNear(Part first, Part last, boolean is_first_depend, boolean is_last_depend) {
                if (first.id >= last.id)
                    return true;
                for (int i = first.id+1; i < last.id; i++) {
                    if (!empty_id.contains(i)) {
                            if (is_first_depend && checkForAttachment(id.get(i), first)) continue;
                            if (is_last_depend && checkForAttachment(id.get(i), last)) continue;
                        return true;
                    }
                }
                return false;
            }

            // возвращает true если adjective уже проверили
            boolean checkForAdjective(Nounable nounable){
                if (checkForActive(nounable.getAdjective())) {
                    if (checkForNotNear(nounable.getAdjective(), (Part)nounable,  false, false))
                        putCommand(new OrderCommand(nounable.getAdjective().name + " must be before " +  ((Part)nounable).name));
                    now = nounable.getAdjective();
                    return false;
                }
                return true;
            }

        };

        // проверяет, является ли элемент ребенком другого
        // тут суть в чем - допустим у нас есть дополнение и определение, причем определение должно стоять после дополнения
        // но и у дополнения и у определения могут быть еще зависимости. Причем они могут стоять между ними.
        // и нужно определить, что стоящие между определением и дополнением слова - должны там стоять, потому что зависят от них
        // переменные depend показывают, есть ли у таргетов какие-то зависимости, которые нужно учитывать
        boolean checkForAttachment(Part now, Part target){
            if (now == null)
                return false;
            /*
            if (now.getClass().getSimpleName().equals("Subject")){
                return now.equals(getClosestSubject(target));
            }

             */
            return now.equals(target) || checkForAttachment(now.back, target);
        }

        Part getClosestSubject(Part now){
            if (now.getClass().getSimpleName().equals("Subject")) return now;
            return getClosestSubject(now.back);
        }

        abstract void check(ArrayList<String> string, Part sentence);

    }

}
