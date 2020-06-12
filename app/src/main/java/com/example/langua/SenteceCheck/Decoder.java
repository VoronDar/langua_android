package com.example.langua.SenteceCheck;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Decoder {

    public static final String PARENT = "=";
    public static final String DIVIDER = "]";
    public static final String ARTICLE = "*";
    public static final String MODAL_VERB = "-";
    public static final String IS_EXCEPTION = "!";
    public static final String TYPE_END = ":";
    public static final String UNION = "/";


    public static ArrayList<Part> allParts = new ArrayList<>();

    public static Part decoder(String coming) {
        /*
           "s:she]!pv:wants=0]av:live*to=1]"
           "s:she]pn:human*a-is=0]"
           "s:she]pv:running-is=0]st:fast*so=1]"
           s:we]pv:going-are=0]av:destroy*to=1]an:world*the=2]ad:entire=3]
           s:i]pv:want=0]av:connect*to=1]an:pictures*these=2]
            s:device*this]pv:should=0]av:be=1]an:here=2]st:you*with=2]
            s:i]pv:filled-am=0]st:strengths*with=1]
            s:i]pv:have=0]an:relationships*a=1]ad:good=2]st:parents*with=1]

            s:i]pv:will=0]av:be=1]an:father*a=2]ad:good=3]sn:children*your-for=2]ad:pretty=5]
            i will be a good father for your pretty children

            s:i]pv:going-am=0]av:magnify*to=1]an:image*this=2]
            s:i]pv:cut=0]an:you=1]sn:knife*a-with=1]

            s:i]!pv:bitten-was=0]
            s:i]pv:tell=0]st:you=1]av:not*do=1]av:touch=3]an:children*the=4]

            s:i]pv:did=0]an:movement*this=1]ad:hard*really=2]svt:prove*to=1]an:possibilities*my=4]
            s:i]pv:did=0]an:movement*this=1]ad:hard*really=2]sv:prove*to/that=1]s:i=4]pv:deal-can=5]sn:difficulties*with=6]ad:any=7]

            s:i]pv:did=0]an:movement=1]sv:prove*to=1]s:i=3]pv:can=4]svt:tomorrow=5]

            s:i]pv:want=0]av:make*to=1]sn:gift*a=2]an:you=2]
            s:i]pv:see=0]an:you=1]stt:time*next=1]



one
s:i]pn:god*a-am=0]
i am a good

            *smt - артикль
            -is - модальный/вспомогательный глагол  или preposition (предлог)
            /that - союз
            =х  - родитель


            ! - в начале - исключение

            Кстати - в конструкции i am ADJECTIVE можно записать adjective как predicateNoun
         */
        //String coming = "s:you]pn:pretty*so`are=0]";
        Pattern mask = Pattern.compile(DIVIDER);
        String[] parts = mask.split(coming);
        ArrayList<Part> preparedParts = new ArrayList<>(parts.length);
        preparedParts.add(new Subject(getName(parts[0]), getArticle(parts[0]), isException(parts[0])));
        allParts.clear();
        allParts.add(preparedParts.get(0));

        if (getArticle(parts[0]) != null)
            allParts.add(((Subject)preparedParts.get(0)).getArticle());

        for (int i = 1; i < parts.length; i++){
            String nowStr = parts[i];
            //System.out.println(nowStr + ", size = " + preparedParts.size() + "type - " + getType(nowStr));
            Part part = preparedParts.get(getParentId(nowStr));
            switch (getType(nowStr)){
                case SettingVerb:
                    SettingVerb settingV = new SettingVerb(getName(nowStr), getArticle(nowStr), getPreposition(nowStr), isException(nowStr), getSettingType(nowStr), setUnion(nowStr));
                    ((Verbable)part).setSetting(settingV);
                    preparedParts.add(settingV);
                    break;
                case SettingNoun:
                    SettingNoun settingN = new SettingNoun(getName(nowStr), getArticle(nowStr), getPreposition(nowStr), getSettingType(nowStr), isException(nowStr));
                    ((Verbable)part).setSetting(settingN);
                    preparedParts.add(settingN);
                    break;
                case Setting:
                    Setting setting = new Setting(getName(nowStr), getArticle(nowStr), getPreposition(nowStr), getSettingType(nowStr));
                    ((Verbable)part).setSetting(setting);
                    preparedParts.add(setting);
                    break;
                case Subject:
                    Subject subject = new Subject(getName(nowStr), getArticle(nowStr), isException(nowStr));
                    ((SettingVerb)part).setSubject(subject);
                    preparedParts.add(subject);
                    break;
                case PredicateNoun:
                    PredicateNoun predicateNoun = new PredicateNoun(getName(nowStr), getArticle(nowStr), getModalVerb(nowStr), isException(nowStr));
                    ((Subject)part).setPredicate(predicateNoun);
                    preparedParts.add(predicateNoun);
                    break;
                case PredicateVerb:
                    PredicateVerb predicate = new PredicateVerb(getName(nowStr), isException(nowStr));
                    ((Subject)part).setPredicate(predicate);
                    predicate.setModalVerb(getModalVerb(nowStr));
                    preparedParts.add(predicate);
                    break;
                case AddictionNoun:
                    AddictionNoun addictionNoun= new AddictionNoun(getName(nowStr), getArticle(nowStr), isException(nowStr));
                    ((Verbable)part).setAddiction(addictionNoun);
                    preparedParts.add(addictionNoun);
                    break;
                case AddictionVerb:
                    AddictionVerb addictionVerb = new AddictionVerb(getName(nowStr), getArticle(nowStr), isException(nowStr));
                    ((Verbable)part).setAddiction(addictionVerb);
                    preparedParts.add(addictionVerb);
                    break;
                case Adjective:
                    Adjective adjective = new Adjective(getName(nowStr), getArticle(nowStr));
                    ((Nounable)part).setAdjective(adjective);
                    preparedParts.add(adjective);
                    break;
            }
            allParts.add(preparedParts.get(preparedParts.size()-1));
            if (getArticle(nowStr) != null)
                try{
                    allParts.add(((ArticleHolder)preparedParts.get(preparedParts.size()-1)).getArticle());
                } catch (Exception ignored){}
            if (getModalVerb(nowStr) != null)
                try{
                    allParts.add(((Predicate)preparedParts.get(preparedParts.size()-1)).getModalVerb());
                } catch (Exception ignored){}
            if (getPreposition(nowStr) != null)
                try{
                    allParts.add(((Setting)preparedParts.get(preparedParts.size()-1)).getPreposition());
                } catch (Exception ignored){}
            if (setUnion(nowStr) != null)
                try{
                    allParts.add(((SettingVerb)preparedParts.get(preparedParts.size()-1)).getUnion());
                } catch (Exception ignored){}
        }
        return preparedParts.get(0);
    }

    private static String getName(String str){
        if (str.contains(ARTICLE))
            return str.substring(str.indexOf(TYPE_END) +1, str.indexOf(ARTICLE));
        else if (str.contains(MODAL_VERB))
            return str.substring(str.indexOf(TYPE_END) +1, str.indexOf(MODAL_VERB));
        else if (str.contains(PARENT))
            return str.substring(str.indexOf(TYPE_END) +1, str.indexOf(PARENT));
        return str.substring(str.indexOf(TYPE_END)+1);
    }
    private static int getParentId(String str){
        return Integer.parseInt(str.substring(str.indexOf(PARENT)+1));
    }

    enum Type{
        Subject,
        PredicateVerb,
        PredicateNoun,
        AddictionVerb,
        AddictionNoun,
        Adjective,
        Setting,
        SettingNoun,
        SettingVerb
    }

    private static Type getType(String str){
        int start = 0;
        if (str.substring(0,1).equals(IS_EXCEPTION))
            start = 1;
        switch (str.substring(start, start + 2)) {
            case "st":
                return Type.Setting;
            case "sn":
                return Type.SettingNoun;
            case "sv":
                return Type.SettingVerb;
            case "pv":
                return Type.PredicateVerb;
            case "pn":
                return Type.PredicateNoun;
            case "av":
                return Type.AddictionVerb;
            case "an":
                return Type.AddictionNoun;
            case "ad":
                return Type.Adjective;
        }
        if (str.charAt(start) == 's') return Type.Subject;
        else if (str.charAt(start) == 'a') return Type.Adjective;
        return null;
    }

    private static Article getArticle(String str){
        if (!str.contains(ARTICLE)) return null;
        if (str.contains(MODAL_VERB))
            return new Article(str.substring(str.indexOf(ARTICLE) +1, str.indexOf(MODAL_VERB)));
        if (str.contains(UNION))
            return new Article(str.substring(str.indexOf(ARTICLE) +1, str.indexOf(UNION)));
        if (str.contains(PARENT))
            return new Article(str.substring(str.indexOf(ARTICLE) +1, str.indexOf(PARENT)));
        return new Article(str.substring(str.indexOf(ARTICLE)+1));
    }

    private static ModalVerb getModalVerb(String str){
        if (!str.contains(MODAL_VERB)) return null;
        if (str.contains(PARENT))
            return new ModalVerb(str.substring(str.indexOf(MODAL_VERB) +1, str.indexOf(PARENT)));
        return new ModalVerb(str.substring(str.indexOf(MODAL_VERB)+1));
    }

    private static Preposition getPreposition(String str){
        if (!str.contains(MODAL_VERB)) return null;
        if (str.contains(UNION))
            return new Preposition(str.substring(str.indexOf(MODAL_VERB) +1, str.indexOf(UNION)));
        else if (str.contains(PARENT))
            return new Preposition(str.substring(str.indexOf(MODAL_VERB) +1, str.indexOf(PARENT)));
        return new Preposition(str.substring(str.indexOf(MODAL_VERB)+1));
    }

    private static Union setUnion(String str){
        if (!str.contains(UNION)) return null;
        if (str.contains(PARENT))
            return new Union(str.substring(str.indexOf(UNION) +1, str.indexOf(PARENT)));
        return new Union(str.substring(str.indexOf(UNION)+1));
    }

    private static SettingType getSettingType(String str){
        switch (str.charAt(2)){
            case 'f':
                return SettingType.frequency;
            case 'a':
                return SettingType.action;
            case 't':
                return SettingType.time;
            case 'r':
                return SettingType.reason;
            default:
                return SettingType.place;
        }
    }


    private static boolean isException(String str){
        return (str.charAt(0) == '!');
    }
}

