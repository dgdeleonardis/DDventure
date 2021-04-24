package logic;


import javafx.util.Pair;

import java.io.File;
import java.util.*;

public class DDventureLogic implements ILogic{

    private static DDventureLogic instance = null;

    private Game game;
    private Map tempMap;
    private Character tempCharacter;
    private XMLSpriteManager xmlSpriteManager;
    private static final String SPRITE_XML_FILE_NAME = "animation.xml";

    private boolean moveMode;
    private ArrayList<Pair<Integer, Integer>> cellsToMove;
    private int turnNumber;

    private boolean attackMode;
    private CharacterInGame enemyToAttack;
    private ArrayList<Pair<Integer, Integer>> cellsToAttack;

    private Team winnerTeam;

    private final ArrayList<CharacterSprite> sprites;

    private static final String[] SPRITE_NAME = {
        "Arcer (M)",
        "Arcer (F)",
        "Assassin (M)",
        "Assassin (F)",
        "Ax (M)",
        "Ax (F)",
        "Mage (M)",
        "Mage (F)",
        "Dark Mage(M)",
        "Dark Mage (F)",
        "Knight (M)",
        "Knight (F)",
        "Swordman (M)",
        "Swodman (F)"
    };

    private final static Weapon[] DEFAULT_WEAPONS = {
            new Weapon("Spada", 8, 2),
            new Weapon("Lancia", 6, 1),
            new Weapon("Pugnale", 4, 3),
            new Weapon("Ascia", 6, 1),
            new Weapon("Magia bianca", 10, 1),
            new Weapon("Magia oscura", 8, 3)
    };

    //private final static HashMap<String, String> WEAPONS_MUGSHOOT = new HashMap<>() {{
        // bla bla bla
    //}};;


    private final static int DEFAULT_COLUMNS = 16;
    private final static int DEFAULT_ROWS = 9;

    private DDventureLogic() {
        // game's directory sets up
        File gameDirectory = new File(System.getProperty("user.home") + "/Documents/DDventure");
        if(!gameDirectory.exists()) {
            gameDirectory.mkdir();
            new File(gameDirectory.getAbsolutePath() + "/saved_game").mkdir();
            new File(gameDirectory.getAbsolutePath() + "/saved_map").mkdir();
            new File(gameDirectory.getAbsolutePath() + "/saved_character").mkdir();
        }
        xmlSpriteManager = new XMLSpriteManager(SPRITE_XML_FILE_NAME);
        sprites = xmlSpriteManager.getCharacterSpriteList();

        game = new Game();
        moveMode = false;
        attackMode = false;
        turnNumber = 0;
    }

    public static ILogic getInstance() {
        if(instance == null)
            instance = new DDventureLogic();
        return instance;
    }


    @Override
    public boolean createTeam(String teamName, String teamColor) {
        for(int i = 0; i < game.getTeams().size(); i++) {
            Team t = game.getTeams().get(i);
            if(teamName.equals(t.getName()) || teamColor.equals(t.getColor())) {
                return false;
            }
        }
        game.getTeams().add(new Team(teamName, teamColor));
        return true;
    }

    @Override
    public void createCharacterInGame(String name, int pf, int ca, int speed, int initiative, String avatar, String teamName, String weaponName, int damageDice, int tpcModifier) {
        Team characterTeam = null;
        for(int i = 0; i < game.getTeams().size(); i++) {
            Team t = game.getTeams().get(i);
            if(t.getName().equals(teamName)) {
                characterTeam = t;
            }
        }
        CharacterInGame characterInGame = new CharacterInGame(name, pf, ca, speed, initiative, avatar, characterTeam,
                new Weapon(weaponName, damageDice, tpcModifier));
        game.getCharactersInGame().add(characterInGame);
        characterTeam.addToTeam(characterInGame);
    }

    @Override
    public void createCharacter(String name, int pf, int ca, int speed, int initiative, String avatar, String weaponName, int damageDice, int tpcModifier) {
        tempCharacter = new Character(name, pf, ca, speed, initiative, avatar,
                new Weapon(weaponName,damageDice, tpcModifier)
        );
    }

    @Override
    public void deleteCharactersInGame() {
        game.getCharactersInGame().clear();
    }

    @Override
    public void deleteTeams() {
        game.getTeams().clear();
    }

    @Override
    public String[] getTeamNames() {
        int n = game.getTeams().size();
        String[] teamNames = new String[n];
        for(int i = 0; i < n; i++)
            teamNames[i] = game.getTeams().get(i).getName();
        return teamNames;
    }

    @Override
    public void createMap(double size) {
        int columns = (int) Math.round(DEFAULT_COLUMNS*size);
        int rows = (int) Math.round(DEFAULT_ROWS*size);
        Cell[][] tempMapCells = new Cell[columns][rows];
        for(int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                tempMapCells[i][j] = new Cell(Map.TERRAIN_MAP.get((int) Math.ceil(Math.random() * 3.0)));
            }
        }
        tempMap = new Map(tempMapCells);
        //TODO: implementare controllo Dijkstra
    }

    @Override
    public Map getTempMap() {
        return tempMap;
    }

    public Character getTempCharacter() {
        return tempCharacter;
    }

    @Override
    public void setGameMap() {
        game.setMap(tempMap);
    }

    @Override
    public Map getGameMap() {
        return game.getMap();
    }

    @Override
    public Game getGame() {
        return game;
    }

    @Override
    public boolean saveCharacter(String fileName) {
        return XMLSaveAndLoadManager.saveCharacter(fileName, tempCharacter);
    }

    @Override
    public boolean loadCharacter(String fileName) {
        Character character = XMLSaveAndLoadManager.loadCharacter(fileName);
        if(character != null) {
            tempCharacter = character;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean saveMap(String fileName) {
        return XMLSaveAndLoadManager.saveMap(fileName, tempMap);
    }

    @Override
    public boolean loadMap(String fileName) {
        Map map = XMLSaveAndLoadManager.loadMap(fileName);
        if(map != null) {
            tempMap = map;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void searchCellsToMove(CharacterInGame characterInGame) {
        cellsToMove = new ArrayList<>();
        int xSource = characterInGame.getCoordinataX();
        int ySource = characterInGame.getCoordinataY();
        int movementLeft = characterInGame.speed;
        Map mapGameCells = game.getMap();
        if(movementLeft - mapGameCells.getCell(xSource, ySource).getCrossingCost() >= 0) {
            cellsToMove.add(new Pair<>(xSource, ySource));
            movementLeft -= mapGameCells.getCell(xSource, ySource).getCrossingCost();
            searchCellsToMoveUpRic(cellsToMove, movementLeft, xSource, ySource - 1);
            searchCellsToMoveDownRic(cellsToMove, movementLeft, xSource, ySource + 1);
            searchCellsToMoveLeftRic(cellsToMove, movementLeft, xSource - 1, ySource);
            searchCellsToMoveRightRic(cellsToMove, movementLeft, xSource + 1, ySource);
        }
        cellsToMove = removeDuplicates(cellsToMove);
    }

    private void searchCellsToMoveUpRic(ArrayList<Pair<Integer, Integer>> cellsToMove, int movementLeft, int iSource, int jSource) {
        Map mapGameCells = game.getMap();
        if((iSource >= 0 && iSource < mapGameCells.getColumns()) && (jSource >= 0 && jSource < mapGameCells.getRows())) {
            if(movementLeft - mapGameCells.getCell(iSource, jSource).getCrossingCost() >= 0) {
                cellsToMove.add(new Pair<>(iSource, jSource));
                movementLeft -= mapGameCells.getCell(iSource, jSource).getCrossingCost();
                searchCellsToMoveLeftRic(cellsToMove, movementLeft, iSource - 1, jSource);
                searchCellsToMoveRightRic(cellsToMove, movementLeft, iSource + 1, jSource);
                searchCellsToMoveUpRic(cellsToMove, movementLeft, iSource, jSource - 1);
            }
        }
    }

    private void searchCellsToMoveLeftRic(ArrayList<Pair<Integer, Integer>> cellsToMove, int movementLeft, int iSource, int jSource) {
        Map mapGameCells = game.getMap();
        if((iSource >= 0 && iSource < mapGameCells.getColumns()) && (jSource >= 0 && jSource < mapGameCells.getRows())) {
            if(movementLeft - mapGameCells.getCell(iSource, jSource).getCrossingCost() >= 0) {
                cellsToMove.add(new Pair<>(iSource, jSource));
                movementLeft -= mapGameCells.getCell(iSource, jSource).getCrossingCost();
                searchCellsToMoveLeftRic(cellsToMove, movementLeft, iSource - 1, jSource);
                searchCellsToMoveDownRic(cellsToMove, movementLeft, iSource, jSource + 1);
                searchCellsToMoveUpRic(cellsToMove, movementLeft, iSource, jSource - 1);
            }
        }
    }

    private void searchCellsToMoveRightRic(ArrayList<Pair<Integer, Integer>> cellsToMove, int movementLeft, int iSource, int jSource) {
        Map mapGameCells = game.getMap();
        if((iSource >= 0 && iSource < mapGameCells.getColumns()) && (jSource >= 0 && jSource < mapGameCells.getRows())) {
            if(movementLeft - mapGameCells.getCell(iSource, jSource).getCrossingCost() >= 0) {
                cellsToMove.add(new Pair<>(iSource, jSource));
                movementLeft -= mapGameCells.getCell(iSource, jSource).getCrossingCost();
                searchCellsToMoveRightRic(cellsToMove, movementLeft, iSource + 1, jSource);
                searchCellsToMoveDownRic(cellsToMove, movementLeft, iSource, jSource + 1);
                searchCellsToMoveUpRic(cellsToMove, movementLeft, iSource, jSource - 1);
            }
        }
    }

    private void searchCellsToMoveDownRic(ArrayList<Pair<Integer, Integer>> cellsToMove, int movementLeft, int iSource, int jSource) {
        Map mapGameCells = game.getMap();
        if((iSource >= 0 && iSource < mapGameCells.getColumns()) && (jSource >= 0 && jSource < mapGameCells.getRows())) {
            if(movementLeft - mapGameCells.getCell(iSource, jSource).getCrossingCost() >= 0) {
                cellsToMove.add(new Pair<>(iSource, jSource));
                movementLeft -= mapGameCells.getCell(iSource, jSource).getCrossingCost();
                searchCellsToMoveRightRic(cellsToMove, movementLeft, iSource + 1, jSource);
                searchCellsToMoveDownRic(cellsToMove, movementLeft, iSource, jSource + 1);
                searchCellsToMoveLeftRic(cellsToMove, movementLeft, iSource - 1, jSource);
            }
        }
    }

    private ArrayList<Pair<Integer, Integer>> removeDuplicates(ArrayList<Pair<Integer, Integer>> list) {
        Set<Pair<Integer, Integer>> set = new HashSet<>();
        set.addAll(list);
        list.clear();
        list.addAll(set);
        return list;
    }

    @Override
    public void generateRandomPositions() {
        Map map = game.getMap();
        game.getCharactersInGame().forEach( characterInGame -> {
            boolean flag = true;
            while(flag) {
                int i = (int) Math.floor(Math.random()*map.getColumns());
                int j = (int) Math.floor(Math.random()*map.getRows());
                if(!map.getCell(i, j).isOccupied() && map.getCell(i, j).getCrossingCost() < Integer.MAX_VALUE) {
                    map.getCell(characterInGame.getCoordinataX(), characterInGame.getCoordinataY()).setCharacterOnCell(null);
                    map.getCell(i, j).setCharacterOnCell(characterInGame);
                    characterInGame.setCoordinataX(i);
                    characterInGame.setCoordinataY(j);
                    flag = false;
                }
            }
        });
    }

    @Override
    public boolean isInMoveMode() {
        return moveMode;
    }
    
    @Override
    public void disableMoveMode() {
        moveMode = false;
    }
    
    @Override
    public void enableMoveMode() {
        moveMode = true;
    }

    @Override
    public Pair<Integer, Integer>[] getCellsToMove() {
        Pair<Integer, Integer>[] array = new Pair[cellsToMove.size()];
      return cellsToMove.toArray(array);
    }

    @Override
    public CharacterInGame getTurnCharacterInGame() {
        return game.getCharactersInGame().get(turnNumber);
    }

    @Override
    public boolean moveCharacterInGame(CharacterInGame characterInGame, int iTarget, int jTarget) {
        boolean flag = false;
        for(int i = 0; i < cellsToMove.size() && !flag; i++) {
            Pair<Integer, Integer> cell = cellsToMove.get(i);
            if(cell.getKey().intValue() == iTarget && cell.getValue().intValue() == jTarget) {
                if(!game.getMap().getCell(iTarget, jTarget).isOccupied())  {
                    game.getMap().getCell(characterInGame.getCoordinataX(), characterInGame.getCoordinataY()).setCharacterOnCell(null);
                    characterInGame.setCoordinataX(iTarget);
                    characterInGame.setCoordinataY(jTarget);
                    game.getMap().getCell(iTarget, jTarget).setCharacterOnCell(characterInGame);
                    flag = true;
                    moveMode = false;
                }
            }
        }
        return flag;
    }

    public ArrayList<CharacterInGame> characterTurnOrder(){

        for (int i = 0; i < game.getCharactersInGame().size(); i++){
            game.getCharactersInGame().get(i).setInitiative((int) (Math.random()*20 +1 + game.getCharactersInGame().get(i).getInitiativeModifier()));
        }

        Comparator<CharacterInGame> initiativeComparator = Comparator.comparing(CharacterInGame::getInitiative).reversed();

        Collections.sort(game.getCharactersInGame(), initiativeComparator);
        return game.getCharactersInGame();
    }

    @Override
    public Pair<Integer, Integer>[] getCellsToAttack() {
        int iSource = this.getTurnCharacterInGame().getCoordinataX();
        int jSource = this.getTurnCharacterInGame().getCoordinataY();
        Team team = this.getTurnCharacterInGame().getTeam();
        cellsToAttack = new ArrayList<>();
        // up
        if(isCellToAttack(team, iSource, jSource - 1))
            cellsToAttack.add(new Pair<>(iSource, jSource - 1));
        if(isCellToAttack(team, iSource, jSource + 1))
            cellsToAttack.add(new Pair<>(iSource, jSource + 1));
        if(isCellToAttack(team, iSource - 1, jSource))
            cellsToAttack.add(new Pair<>(iSource - 1, jSource));
        if(isCellToAttack(team, iSource + 1, jSource))
            cellsToAttack.add(new Pair<>(iSource + 1, jSource));
        Pair<Integer, Integer>[] cellsToAttackArray = new Pair[cellsToAttack.size()];
        return cellsToAttack.toArray(cellsToAttackArray);
    }

    @Override
    public void setAttackMode(boolean attackMode) {
        this.attackMode = attackMode;
    }

    @Override
    public boolean isInAttackMode() {
        return attackMode;
    }

    private boolean isCellToAttack(Team team, int i, int j) {
        Map map = game.getMap();
        if((i < map.getColumns() && i >= 0) && (j>= 0 && j < map.getRows())) {
            if(map.getCell(i, j).isOccupied()) {
                if(!map.getCell(i, j).getCharacterOnCell().getTeam().equals(team)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isAttackable(Pair<Integer, Integer> selectedCell) {
        for(int i = 0; i < cellsToAttack.size(); i++) {
            if(selectedCell.getKey().intValue() == cellsToAttack.get(i).getKey().intValue() &&
                    selectedCell.getValue().intValue() == cellsToAttack.get(i).getValue().intValue()) {
                enemyToAttack = game.getMap().getCell(selectedCell.getKey().intValue(), selectedCell.getValue().intValue()).getCharacterOnCell();
                return true;
            }
        }
        return false;
    }

    public String getCharacterName(int i){
        return game.getCharactersInGame().get(i).getName();
    }

    public void resetCharacter(int i){
        game.getCharactersInGame().get(i).setHasAttacked(false);
        game.getCharactersInGame().get(i).setRemainingSpeed(game.getCharactersInGame().get(i).getSpeed());
    }

    @Override
    public boolean isCABreak(int result) {
        int tpcModifier = getTurnCharacterInGame().getWeapon().getTpcModifier();
        int enemyCA = enemyToAttack.getCA();
        if((result + tpcModifier) >= enemyCA) {
            return true;
        } else {
            return false;
        }
    }

    public CharacterSprite getTurnCharacterInGameSprite() {
        CharacterSprite sprite;
        for(int i = 0; i < sprites.size(); i++) {
            sprite = sprites.get(i);
            if(sprite.getName().equals(getTurnCharacterInGame().getAvatar())) {
                return sprite;
            }
        }
        return null;
    }

    @Override
    public void damageControll(int damage) {
        enemyToAttack.decreaseRemainingHP(damage);
        if (enemyToAttack.getRemainingHP() <= 0) {
            int xEnemy = enemyToAttack.getCoordinataX();
            int yEnemy = enemyToAttack.getCoordinataY();
            game.getMap().getCell(xEnemy, yEnemy).setCharacterOnCell(null);
            enemyToAttack.setCoordinataX(-1);
            enemyToAttack.setCoordinataY(-1);
        }
    }

    @Override
    public boolean isEnemyDead() {
        return enemyToAttack.getRemainingHP() <= 0;
    }

    public boolean isThereAWinner() {
        ArrayList<Team> teams = game.getTeams();
        int remainedTeams = 0;
        Team winnerTeam = null;
        int i = 0;
        while(remainedTeams < 2 && i < teams.size()) {
            if(teams.get(i).isInGame()) {
                winnerTeam = teams.get(i);
                remainedTeams++;
            }
            i++;
        }
        if(remainedTeams == 1) {
            this.winnerTeam = winnerTeam;
            return true;
        } else {
            return false;
        }
    }

    public String getWinnerTeamName() {
        return winnerTeam.getName();
    }

    public String[] getWinnerTeamMembers() {
        CharacterInGame[] members = winnerTeam.getMembers();
        String[] arr = new String[members.length];
        for(int i = 0; i < members.length; i++) {
            arr[i] = members[i].getName();
        }
        return arr;
    }

    @Override
    public void resetGame() {
        game = new Game();
        tempMap = null;
        tempCharacter = null;

        moveMode = false;
        //cellsToMove.clear();
        turnNumber = 0;

        attackMode = false;
        enemyToAttack = null;
        //cellsToAttack.clear();

        winnerTeam = null;
    }
}
