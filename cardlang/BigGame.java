package Game2018;

import java.util.Vector;

class BigGame extends GameEngine {
    boolean[] flags;
    int[] points;
    Card card;

    public BigGame(int num_players) {
        super(num_players);
    } 

    public void initCards(int num_players) {
        // pre-defined
        numPlayers = num_players;
        deck = new Deck();
        hands = new Hand[numPlayers];
        flags = new boolean[numPlayers];
        points = new int[numPlayers];
        for (int z = 0; z < numPlayers; ++z) {
            hands[z] = new Hand(z, getPlayerChar(z));
        }
        prevPlay = new Hand(-1, ' ');
        int _Game = 0;

        // game-specific
        deck.shuffle();
        int i_id = 1;
        for (int i_i = 1; i_i <= 52; ++i_i) {
            card = deck.drawCard();
            hands[i_id-1].addCard(card);
            i_id = i_id + 1;
            if (i_id > numPlayers) {
                i_id = 1;
            }
        }
        _Game = 52 / numPlayers + 1;

        // pre-defined
        displaySize = _Game;
        curPlayerID = 0;
        winnerID = -1;
    }

    public String getStatus() {
        // pre-defined
        String s="[Game cards]\n"+"Deck: "+deck.cardCount()+", Dump: "+deck.dumpCount()+"\n\n";
		
		s=s+"[Players' cards]\n";
		for (int z=0;z<numPlayers;z++) {
			char ch=getPlayerChar(z);
			s=s+ch+":"+hands[z].cards.size()+" ";
		}
        s=s+"\n\n";
        
        s=s+"[Players' points]\n";
		for (int z=0;z<numPlayers;z++) {
			char ch=getPlayerChar(z);
			s=s+ch+":"+ points[z] +" ";
		}
        s=s+"\n\n";
        
        String _Screen = "";

        // game-specific

        // pre-defined
        s=s+_Screen;
        if (!isCompleted) {
            s=s+"*** Current player "+getPlayerChar(curPlayerID);
        }

        if (isCompleted) {
            if (winnerID < 0) {
                s = s + "*** Draw";
            } else {
                s=s+"*** Winner: "+getPlayerChar(winnerID);
            }
        }
        
		return s;
    }

    public boolean isValidCombination(boolean[] isCardSelected) {
        // pre-defined
        Hand curPlay = new Hand(curPlayerID, getPlayerChar(curPlayerID));
        Vector<Card> curCards = hands[curPlayerID].cards;
        for (int pos = 0; pos < curCards.size(); ++pos) {
            if (isCardSelected[pos]) {
                curPlay.addCard(curCards.get(pos));
            }
        }
        boolean _Valid = false;

        // game-specific
        if (curPlay.cards.size() != 1) {
            _Valid = false;
        } else {
            if (prevPlay.playerID+1 == 0 || prevPlay.playerID+1 == curPlayerID+1 || curPlay.cards.get(1-1).getRank() == 1) {
                _Valid = true;
            } else {
                if (prevPlay.cards.get(1-1).getRank() == 1) {
                    _Valid = false;
                } else {
                    _Valid = curPlay.cards.get(1-1).getRank() > prevPlay.cards.get(1-1).getRank();
                }
            }
        }

        // pre-defined
        return _Valid;
    }

    public boolean isWinnerFound() {
        // pre-defined
        if (isCompleted) {
            return isCompleted;
        }
        isCompleted = false;
        int _WinnerID = -1;

        // game-specific
        for (int i_i = 1; i_i <= numPlayers; ++i_i) {
            if (hands[i_i-1].cards.size() == 0) {
                _WinnerID = i_i;
            }
        }
        
        // pre-defined
        if (_WinnerID >= 0) {
            isCompleted = true;
            winnerID = _WinnerID - 1;
        }
        return isCompleted;
    }

    public void nextTurn() {
        // pre-defined
        int _NextID = curPlayerID + 1;

        // game-specific
        _NextID = (curPlayerID + 1) % numPlayers + 1;

        // pre-defined
        curPlayerID = _NextID - 1;
        ui.setCurHand(hands[curPlayerID]);
        ui.drawHand();
    }
}