package com.xsp.texas;

/**
 * Created by shpng on 2016/10/23.
 */
public class Player {

    public Card[] cards = new Card[2];
    public Combination bestCombination;
    public int index; // 位置
    public PlayerStatus playerStatus;
    public int currentRoundCost = 0; // 本轮已加注，用来记录中间已经加注的情况，如果其他人raise，则恶

    public int bankroll; // 资本，手上的筹码

    public boolean ai; // true 表示是AI

    public RoundAction action(BettingRounds bettingRounds, Match match, int currentAdd) {
        RoundAction roundAction = new RoundAction();
        roundAction.bettingRounds = bettingRounds;
        roundAction.originAmount = currentRoundCost;
        roundAction.playerIndex = index;
        // TODO
        Action action = getAction();

        if (action == Action.CHECK) {
            // 不能check 则fold
            if (currentAdd > currentRoundCost) {
                action = Action.FOLD;
            }

        } else if (action == Action.BET || action == Action.RAISE || action == Action.RERAISE) {

            if (currentAdd > 0 && action == Action.BET) {
                action = Action.RAISE;
            }
            if (currentAdd == 0 && (action == Action.RERAISE || action == Action.RAISE)) {
                action = Action.BET;
            }
            int left = bankroll - (currentAdd - currentRoundCost);
            if (left <= 0) {
                action = Action.ALLIN;
                roundAction.amount = bankroll;
                bankroll -= roundAction.amount;
            } else {
                roundAction.amount = (currentAdd - currentRoundCost)
                        + Math.max(1, (int) (Math.random() * left * 0.2));
                bankroll -= roundAction.amount;
            }

        } else if (action == Action.CALL) {
            int left = bankroll - (currentAdd - currentRoundCost);
            if (left <= 0) {
                action = Action.ALLIN;
                roundAction.amount = bankroll;
                bankroll -= roundAction.amount;
            } else {
                roundAction.amount = (currentAdd - currentRoundCost);
                bankroll -= roundAction.amount;
            }
        }
        roundAction.action = action;

        currentRoundCost += roundAction.amount;
        return roundAction;
    }

    public void calcBestCombination(Match match) {
        if (playerStatus == PlayerStatus.FOLD) {
            bestCombination = null;
            return;
        }
        bestCombination = Main.getBestFiveCombination(cards[0], cards[1], match.tableCards[0]
                , match.tableCards[1], match.tableCards[2], match.tableCards[3], match.tableCards[4]);
    }

    public Action getAction() {
        //BET, CALL, FOLD, CHECK, RAISE, RERAISE, ALLIN
        int[] actionProp = new int[]{8, 35, 68, 90, 96, 98, 100};
        int number = (int) (Math.random() * 100.0);
        for (int i = 0; i < actionProp.length; i++) {
            if (number < actionProp[i]) {
                switch (i + 1) {
                    case 1:
                        return Action.BET;
                    case 2:
                        return Action.CALL;
                    case 3:
                        return Action.FOLD;
                    case 4:
                        return Action.CHECK;
                    case 5:
                        return Action.RAISE;
                    case 6:
                        return Action.RERAISE;
                    case 7:
                        return Action.ALLIN;
                }
            }

        }
        return Action.CHECK;
    }

}
