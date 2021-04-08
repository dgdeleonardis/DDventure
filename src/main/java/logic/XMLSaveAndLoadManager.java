package logic;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;


public class XMLSaveAndLoadManager {

    public static boolean saveGame(String fileName) {
        DocumentBuilder db = XMLInitialization();
        if (db != null) {
            Document doc = db.newDocument();
            doc.appendChild(createDOMGame(doc));
            return writeXML(doc, fileName);
        } else
            return false;
    }

    //Metodo per il salvataggio in XML della mappa
    public static boolean saveMap(String fileName, Cell[][] map) {
        DocumentBuilder db = XMLInitialization();
        if(db != null) {
            Document doc = db.newDocument();
            doc.appendChild(createDOMMap(doc, map));
            return writeXML(doc, fileName);
        }
        return true;
    }

    //Metodo per il salvataggio in XML di un personaggio
    public static boolean saveCharacter(String fileName, Character character) {
        DocumentBuilder db = XMLInitialization();
        if (db != null) {
            Document doc = db.newDocument();
            doc.appendChild(createDOMCharacter(doc, character));
            return writeXML(doc, fileName);
        }
        return true;
    }

    // Metodo per il salvataggio in XML della partita con nome del file dato


    private static Element createDOMGame(Document doc) {
        Element game = doc.createElement("game");
        //FIXME: aggiungere il turno come attributo di game
        game.appendChild(createDOMMap(doc, DDventureLogic.getInstance().getGameMap().getCells()));
        DDventureLogic.getInstance().getGame().getTeams().forEach(
                t -> game.appendChild(createDOMTeam(doc, t))
        );
        return game;
    }

    private static Element createDOMTeam(Document doc, Team t) {
        Element team = doc.createElement("team");
        team.setAttribute("name", t.getName());
        team.setAttribute("color", t.getColor());
        for (CharacterInGame member : t.getMembers()) {
            team.appendChild(createDOMCharacterInGame(doc, member));
        }
        return team;
    }

    private static Element createDOMCharacterInGame(Document doc, CharacterInGame c) {
        Element characterInGame = doc.createElement("characterInGame");
        characterInGame.setAttribute("name", c.getName());
        characterInGame.setAttribute("pf", Integer.toString(c.getPF()));
        characterInGame.setAttribute("ca", Integer.toString(c.getCA()));
        characterInGame.setAttribute("speed", Integer.toString(c.getSpeed()));
        characterInGame.setAttribute("iniative", Integer.toString(c.getInitiative()));
        characterInGame.setAttribute("avatar", c.getAvatar());
        characterInGame.setAttribute("coordinataX", Integer.toString(c.getCoordinataX()));
        characterInGame.setAttribute("coordinataY", Integer.toString(c.getCoordinataY()));
        characterInGame.setAttribute("turnOrder", Integer.toString(c.getTurnOrder()));
        characterInGame.setAttribute("hasAttacked", Boolean.toString(c.isHasAttacked()));
        characterInGame.appendChild(createDOMWeapon(doc, c.getWeapon()));
        return characterInGame;
    }

    private static Element createDOMCharacter(Document doc, Character c) {
        Element character = doc.createElement("character");
        character.setAttribute("name", c.getName());
        character.setAttribute("pf", Integer.toString(c.getPF()));
        character.setAttribute("ca", Integer.toString(c.getCA()));
        character.setAttribute("speed", Integer.toString(c.getSpeed()));
        character.setAttribute("iniative", Integer.toString(c.getInitiative()));
        character.setAttribute("avatar", c.getAvatar());
        character.appendChild(createDOMWeapon(doc, c.getWeapon()));
        return character;
    }

    private static Element createDOMWeapon(Document doc, Weapon weapon) {
        Element weaponElement = doc.createElement("weapon");
        weaponElement.setAttribute("name", weapon.getName());
        weaponElement.setAttribute("damageDice", Integer.toString(weapon.getDamageDice()));
        weaponElement.setAttribute("tpcModifier", Integer.toString(weapon.getTpcModifier()));
        return weaponElement;
    }

    private static Element createDOMMap(Document doc,  Cell[][] modelMap) {
        Element map = doc.createElement("map");
        map.setAttribute("columns", Integer.toString(modelMap.length));
        map.setAttribute("rows", Integer.toString(modelMap[0].length));
        for(int i = 0; i < modelMap.length; i++) {
            for(int j = 0; j < modelMap[i].length; j++) {
                Element cell = doc.createElement("cell");
                cell.setAttribute("column", Integer.toString(i));
                cell.setAttribute("row", Integer.toString(j));
                cell.setAttribute("occupied", Boolean.toString(modelMap[i][j].isOccupied()));
                cell.setAttribute("crossingCost", Integer.toString(modelMap[i][j].getCrossingCost()));
                map.appendChild(cell);
            }
        }
        return map;
    }

    private static DocumentBuilder XMLInitialization() {
        DocumentBuilder db = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
        return db;
    }

    private static boolean writeXML(Document doc, String fileName) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource domSource = new DOMSource(doc);
            // FIXME: salvare il file nella cartella apposita

            StreamResult streamResult = new StreamResult(new File( fileName + ".xml"));
            t.transform(domSource, streamResult);
            return true;
        } catch (TransformerException te) {
            te.printStackTrace();
            return false;
        }
    }

    public static Game loadGame(String fileName) {
        Game game = null;
        DocumentBuilder db = XMLInitialization();
        if(db != null) {
            try {
                Document doc = db.parse(new File(fileName));
                game = new Game();
                NodeList gameNodeList = doc.getElementsByTagName("game");
                for(int i = 0; i < gameNodeList.getLength(); i++) {
                    Node gameNode = gameNodeList.item(i);
                    if(gameNode.getNodeType() == Node.ELEMENT_NODE) {
                        NodeList childNodeList = gameNode.getChildNodes();
                        for(int j = 0; j < childNodeList.getLength(); j++) {
                            Node childNode = childNodeList.item(j);
                            if(childNode.getNodeType() == Node.ELEMENT_NODE) {
                                if(childNode.getNodeName().equals("map")) {
                                    Element map = (Element) childNode;
                                    game.setMap(readDOMMAp(map));
                                } else if(childNode.getNodeName().equals("team")) {
                                    Element team = (Element) childNode;
                                    game.getTeams().add(readDOMTeam(team));
                                }
                            }
                        }
                    }
                }
            }
            catch (SAXException | IOException e) {
                e.printStackTrace();
            }
        }
        return game;
    }

    private static Map readDOMMAp(Element mapNode) {
        int columns = Integer.getInteger(mapNode.getAttribute("columns"));
        int rows = Integer.getInteger(mapNode.getAttribute("rows"));
        Cell[][] mapCells = new Cell[columns][rows];

        NodeList childNodeList = mapNode.getChildNodes();
        for(int i = 0; i < childNodeList.getLength(); i++) {
            Node childNode = childNodeList.item(i);
            if(childNode.getNodeType() == Node.ELEMENT_NODE) {
                Element cell = (Element) childNode;
                int column = Integer.getInteger(cell.getAttribute("column"));
                int row = Integer.getInteger(cell.getAttribute("row"));
                mapCells[column][row] = new Cell(Integer.getInteger(cell.getAttribute("costoAttraversamento")));
            }
        }
        return new Map(mapCells);
    }
    private static Team readDOMTeam(Element teamNode) {
        String name = teamNode.getAttribute("name");
        String color = teamNode.getAttribute("color");
        Team team = new Team(name, color);

        NodeList childNodeList = teamNode.getChildNodes();
        for(int i = 0; i < childNodeList.getLength(); i++) {
            Node childNode = childNodeList.item(i);
            if(childNode.getNodeType() == Node.ELEMENT_NODE) {
                Element characterInGame = (Element) childNode;
                team.addToTeam(readDOMCharacterInGame(characterInGame, team));
            }
        }
        for (CharacterInGame member : team.getMembers()) {
            member.setTeam(team);
        }
        return team;
    }

    private static CharacterInGame readDOMCharacterInGame(Element characterInGameNode, Team characterTeam) {
        Weapon characterWeapon = null;
        NodeList childNodeList = characterInGameNode.getChildNodes();
        for(int i = 0; i < childNodeList.getLength(); i++) {
            Node childNode = childNodeList.item(i);
            if(childNode.getNodeType() == Node.ELEMENT_NODE && childNode.getNodeName().equals("weapon")) {
                Element weaponElement = (Element) childNode;
                characterWeapon = new Weapon(weaponElement.getAttribute("name"),
                        Integer.parseInt(weaponElement.getAttribute("damageDice")),
                        Integer.parseInt(weaponElement.getAttribute("tpcModifier")));
            }
        }
        CharacterInGame characterInGame = new CharacterInGame(characterInGameNode.getAttribute("name"),
                Integer.parseInt(characterInGameNode.getAttribute("pf")),
                Integer.parseInt(characterInGameNode.getAttribute("ca")),
                Integer.parseInt(characterInGameNode.getAttribute("speed")),
                Integer.parseInt(characterInGameNode.getAttribute("initiative")),
                characterInGameNode.getAttribute("avatar"),
                characterTeam, characterWeapon);
        characterInGame.setCoordinataX(Integer.parseInt(characterInGameNode.getAttribute("coordinataX")));
        characterInGame.setCoordinataY(Integer.parseInt(characterInGameNode.getAttribute("coordinataY")));
        characterInGame.setTurnOrder(Integer.getInteger(characterInGameNode.getAttribute("turnOrder")));
        characterInGame.setHasAttacked(Boolean.getBoolean(characterInGameNode.getAttribute("hasAttacked")));
        return characterInGame;
    }
}
