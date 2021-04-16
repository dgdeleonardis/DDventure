package logic;

import javafx.geometry.Rectangle2D;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class XMLSpriteManager {

    private Document doc;

    public XMLSpriteManager(String xmlFileName) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(new File(xmlFileName));
        } catch(ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            doc = null;
        }
    }

    public ArrayList<CharacterSprite> getCharacterSpriteList() {
        ArrayList<CharacterSprite> characterSprites = new ArrayList<>();

        if (doc != null) {
            NodeList characterNodeList = doc.getElementsByTagName("character");
            for (int i = 0; i < characterNodeList.getLength(); i++) {
                Node characterNode = characterNodeList.item(i);
                if (characterNode.getNodeType() == Node.ELEMENT_NODE) {
                    characterSprites.add(getCharacterSprite(characterNode));
                }
            }
        }
        return characterSprites;
    }

    private CharacterSprite getCharacterSprite(Node characterNode) {
        Rectangle2D stance = null;
        ArrayList<Action> actions = new ArrayList<>();
        NamedNodeMap characterNodeAttributes = characterNode.getAttributes();
        NodeList childNodeList = characterNode.getChildNodes();
        for (int i = 0; i < childNodeList.getLength(); i++) {
            Node childNode = childNodeList.item(i);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                if (childNode.getNodeName().equals("stance")) {
                    stance = getStanceRectangle2D(childNode);
                } else if (childNode.getNodeName().equals("action")) {
                    actions.add(getAction(childNode));
                }
            }
        }
        return new CharacterSprite(
                characterNodeAttributes.getNamedItem("name").getNodeValue(),
                characterNodeAttributes.getNamedItem("sheet").getNodeValue(),
                characterNodeAttributes.getNamedItem("backgroundColor").getNodeValue(),
                stance,
                actions
        );
    }

    private Action getAction(Node actionNode) {
        NamedNodeMap actionNodeAttributes = actionNode.getAttributes();
        ArrayList<Rectangle2D> frameList = new ArrayList<>();
        NodeList frameNodeList = actionNode.getChildNodes();
        for(int i = 0; i < frameNodeList.getLength(); i++) {
            Node frameNode = frameNodeList.item(i);
            if(frameNode.getNodeType() == Node.ELEMENT_NODE) {
                frameList.add(getRectangle2D(frameNode));
            }
        }
        return new Action(actionNodeAttributes.getNamedItem("name").getNodeValue(),
                frameList);
    }

    private Rectangle2D getStanceRectangle2D(Node stanceNode) {
        int i = 0;
        NodeList childNodeList = stanceNode.getChildNodes();
        while(childNodeList.item(i).getNodeType() != Node.ELEMENT_NODE)
            i++;
        return getRectangle2D(childNodeList.item(i));
    }

    private Rectangle2D getRectangle2D(Node rectNode) {
                Element rectElement = (Element) rectNode;
                return new Rectangle2D(
                        Double.parseDouble(rectElement.getAttribute("minX")),
                        Double.parseDouble(rectElement.getAttribute("minY")),
                        Double.parseDouble(rectElement.getAttribute("width")),
                        Double.parseDouble(rectElement.getAttribute("height"))
                );
            }

}
