package com.xsp.texas;

import java.util.*;

/**
 * Created by shpng on 2018/1/27.
 */
public class Match {

    /*

    Betting Rounds 押注圈 - 每一个牌局可分为四个押注圈。每一圈押注由按钮（庄家）左侧的玩家开始叫注。

    　Pre-flop 底牌权 / 前翻牌圈 - 公共牌出现以前的第一轮叫注。

　　Flop round 翻牌圈 - 首三张公共牌出现以后的押注圈。

　　Turn round 转牌圈 - 第四张公共牌出现以后的押注圈。

　　River round 河牌圈 - 第五张公共牌出现以后 ， 也即是摊牌以前的押注圈。
     */


    // 玩家数
    public int playerCount;

    // 玩家
    public Player [] players;

    // 每个玩家下的筹码
    public int [] playerCost;

    // 玩家状态
    public PlayerStatus[] playerStatus;

    // 牌
    public Card tableCards[] = new Card[5];

    // 底池
    public int pot;

    // 小盲注
    public int smallBlindValue = 1;

    // 庄家位置
    public int buttonPlayerIndex = 0; // 庄家位

    // 当前行动的玩家
    public int currentPlayerIndex = 0;

    // 每一番最后一个加注的玩家，若没人raise，则是小盲
    public int lastAddPlayer = -1;


    public int currentRoundAdd = 0;




    // 每个押注圈的加注情况
    public Map<BettingRounds, List<RoundAction>> actions = new TreeMap<>();

    Match(int lastButtonPlayerIndex, Player[] players) {

        pot = 0;

        actions.put(BettingRounds.PREFLOP, new ArrayList<>());
        actions.put(BettingRounds.FLOP, new ArrayList<>());
        actions.put(BettingRounds.TURN, new ArrayList<>());
        actions.put(BettingRounds.RIVER, new ArrayList<>());
        this.playerCount = players.length;
        this.players = players;
        this.playerStatus = new PlayerStatus[playerCount];
        this.playerCost = new int[playerCount];
        for (int i = 0; i < playerCount; i++) {
            players[i].index = i;
            players[i].playerStatus = PlayerStatus.PLAYING;
            playerStatus[i] = PlayerStatus.PLAYING;
            playerCost[i] = 0;
        }



        // 换庄家
        this.buttonPlayerIndex = (lastButtonPlayerIndex + 1) % playerCount;

        // 小盲第一个行动
        this.currentPlayerIndex = (buttonPlayerIndex + 1) % playerCount;

        // 先把牌发好
        //Main.recoverDataFromFile();
        List<Card> cards = new ArrayList<>(Main.cards);
        for (int i = 0; i < playerCount; i++) {
            players[(currentPlayerIndex + i) % playerCount].cards[0] = Main.getRandomCard(cards);
        }
        for (int i = 0; i < playerCount; i++) {
            players[(currentPlayerIndex + i) % playerCount].cards[1] = Main.getRandomCard(cards);
        }
        for (int i = 0; i < tableCards.length; i++) {
            Main.getRandomCard(cards);
            tableCards[i] = Main.getRandomCard(cards);
        }
    }

    public void check() {
        // 检查是否都allin或者已经结束或者进入下一round
    }


    /**
     *
     * @param round
     * @param playIndex
     * @param action
     * @param amount
     * @param lastAction 当前round的上一个动作
     */
    public void matchPlaying(BettingRounds round, int playIndex, Action action,
                             int amount, RoundAction lastAction, int lastRaisePlayerIndex) {
        // 检查当前行动是否可行

        if (lastAction == null) {
            // 当前round的第一个，允许 check fold raise allin
        }

        RoundAction roundAction = new RoundAction();
        roundAction.bettingRounds = round;
        roundAction.playerIndex = playIndex;
        roundAction.action = action;
        roundAction.amount = amount;
        actions.get(round).add(roundAction);
        if (action == Action.FOLD) {
            playerStatus[playIndex] = PlayerStatus.FOLD;
        }


    }

    public void getExpectation() {
        // 底池
    }

    public static void main1(String[] args) {
        Player[] ps = new Player[6];
        for(int i=0;i<ps.length;i++) {
            Player p = new Player();
            p.bestCombination = null;
            p.bankroll = 100;
            ps[i] = p;
        }
        Match m = new Match(0, ps);
        m.play();
    }

    public static void main(String[] args) {
        int i = 5;
        int j = 3;
        int k = 2;
        if (i == 5 && j == 3 && k == 2) {
            j =3;
            System.out.println("gg");
        } else if (i == 5 && j == 3 && k==1) {
            System.out.println("uu");
        } else if (i == 5) {
            System.out.println("hh");
        }
    }

    public void showRoundAction(List<RoundAction> roundActions) {
        String ss[] = new String[playerCount];
        Arrays.fill(ss, String.format("%7s", "-"));
        int curIndex = -1;
        int lastIndex = -1;
        for (RoundAction roundAction : roundActions) {
            curIndex = roundAction.playerIndex;

            //if ((lastIndex >= 0 && curIndex < lastIndex)|| (!StringUtils.equals(ss[curIndex].trim(), "-"))) {
            if (lastIndex >= 0 && curIndex < lastIndex) {
                for (int i = 0; i < ss.length; i++) {
                    if (curIndex != i && StringUtils.equals(ss[i].trim(), "-") && playerStatus[i] == PlayerStatus.FOLD) {
                        ss[i] = String.format("%7s", "X");
                    } else if (curIndex != i && StringUtils.equals(ss[i].trim(), "-") && playerStatus[i] == PlayerStatus.ALLIN) {
                        ss[i] = String.format("%7s", "A");
                    } else if (curIndex != i) {
                        ss[i] = String.format("%7s", "(" + players[i].currentRoundCost + ")");
                    }

                }
                System.out.println(Arrays.toString(ss));
                ss = new String[playerCount];
                Arrays.fill(ss, String.format("%7s", "-"));
            }
            if (roundAction.action == Action.FOLD) {
                ss[roundAction.playerIndex] = String.format("%7s", "FOLD");
            } else {
                ss[roundAction.playerIndex] = String.format("%7s", "" + roundAction.amount);
                if (roundAction.action == Action.BET) {
                    ss[roundAction.playerIndex] = String.format("%7s", "r" + roundAction.amount);
                }
                if (roundAction.action == Action.RAISE) {
                    ss[roundAction.playerIndex] = String.format("%7s", "r" + roundAction.amount);
                }
                if (roundAction.action == Action.RERAISE) {
                    ss[roundAction.playerIndex] = String.format("%7s", "rr" + roundAction.amount);
                }
                if (roundAction.action == Action.ALLIN) {
                    ss[roundAction.playerIndex] = String.format("%7s", "A+" + roundAction.amount);
                }
            }
            
            lastIndex = curIndex;

        }
        for (int i = 0; i < ss.length; i++) {
            if (StringUtils.equals(ss[i].trim(), "-") && playerStatus[i] == PlayerStatus.FOLD) {
                ss[i] = String.format("%7s", "X");
            }
            if (StringUtils.equals(ss[i].trim(), "-") && playerStatus[i] == PlayerStatus.ALLIN) {
                ss[i] = String.format("%7s", "A");
            }
        }
        System.out.println(Arrays.toString(ss) + " pot " + pot);
        System.out.println();
    }

    /**
     * 只展示一行的做法
     * @param roundActions
     */
    public void showRoundAction2(List<RoundAction> roundActions) {
        String ss[] = new String[playerCount];
        Arrays.fill(ss, String.format("%7s", "-"));
        int curIndex = -1;
        int lastIndex = -1;
        for (RoundAction roundAction : roundActions) {
            curIndex = roundAction.playerIndex;

            //if ((lastIndex >= 0 && curIndex < lastIndex)|| (!StringUtils.equals(ss[curIndex].trim(), "-"))) {
            if (lastIndex >= 0 && curIndex < lastIndex) {
                for (int i = 0; i < ss.length; i++) {
                    if (curIndex != i && StringUtils.equals(ss[i].trim(), "-") && playerStatus[i] == PlayerStatus.FOLD) {
                        ss[i] = String.format("%7s", "X");
                    } else if (curIndex != i && StringUtils.equals(ss[i].trim(), "-") && playerStatus[i] == PlayerStatus.ALLIN) {
                        ss[i] = String.format("%7s", "A");
                    } else if (curIndex != i) {
                        ss[i] = String.format("%7s", "(" + players[i].currentRoundCost + ")");
                    }

                }
                System.out.println(Arrays.toString(ss));
                ss = new String[playerCount];
                Arrays.fill(ss, String.format("%7s", "-"));
            }
            if (roundAction.action == Action.FOLD) {
                ss[roundAction.playerIndex] = String.format("%7s", "FOLD");
            } else {
                ss[roundAction.playerIndex] = String.format("%7s", "" + roundAction.amount);
                if (roundAction.action == Action.BET) {
                    ss[roundAction.playerIndex] = String.format("%7s", "r" + roundAction.amount);
                }
                if (roundAction.action == Action.RAISE) {
                    ss[roundAction.playerIndex] = String.format("%7s", "r" + roundAction.amount);
                }
                if (roundAction.action == Action.RERAISE) {
                    ss[roundAction.playerIndex] = String.format("%7s", "rr" + roundAction.amount);
                }
                if (roundAction.action == Action.ALLIN) {
                    ss[roundAction.playerIndex] = String.format("%7s", "A+" + roundAction.amount);
                }
            }

            lastIndex = curIndex;

        }
        for (int i = 0; i < ss.length; i++) {
            if (StringUtils.equals(ss[i].trim(), "-") && playerStatus[i] == PlayerStatus.FOLD) {
                ss[i] = String.format("%7s", "X");
            }
            if (StringUtils.equals(ss[i].trim(), "-") && playerStatus[i] == PlayerStatus.ALLIN) {
                ss[i] = String.format("%7s", "A");
            }
        }
        System.out.println(Arrays.toString(ss) + " pot " + pot);
        System.out.println();
    }

    public void play() {

        for (BettingRounds bettingRounds : actions.keySet()) {
            System.out.println(bettingRounds +", 底池：" + pot);
            List<RoundAction> roundActions = actions.get(bettingRounds);
            currentPlayerIndex = (buttonPlayerIndex + 1) %playerCount; // 每次都是小盲开始
            Player player = players[currentPlayerIndex];
            lastAddPlayer = -1;
            if(bettingRounds == BettingRounds.PREFLOP) {
                // 小盲
                RoundAction roundAction = new RoundAction();
                roundAction.playerIndex = currentPlayerIndex;

                if (player.bankroll < smallBlindValue) {
                    roundAction.originAmount = 0;
                    roundAction.amount = player.bankroll;
                    roundAction.bettingRounds = bettingRounds;
                    roundAction.action = Action.ALLIN;
                    pot += roundAction.amount;
                    player.bankroll = 0;
                    player.currentRoundCost = roundAction.amount;
                    playerStatus[currentPlayerIndex] = PlayerStatus.ALLIN;
                    playerCost[currentPlayerIndex] = roundAction.amount;
                } else {
                    roundAction.originAmount = 0;
                    roundAction.amount = smallBlindValue;
                    roundAction.bettingRounds = bettingRounds;
                    roundAction.action = Action.BET;
                    pot += roundAction.amount;
                    player.bankroll -= smallBlindValue;
                    player.currentRoundCost = roundAction.amount;
                    playerCost[currentPlayerIndex] = roundAction.amount;
                }

                roundActions.add(roundAction);
                // 大盲
                player = getNextPlayer();
                roundAction = new RoundAction();
                roundAction.playerIndex = currentPlayerIndex;
                if (player.bankroll < smallBlindValue * 2) {
                    roundAction.originAmount = 0;
                    roundAction.amount = player.bankroll;
                    roundAction.bettingRounds = bettingRounds;
                    roundAction.action = Action.ALLIN;
                    pot += roundAction.amount;
                    player.bankroll = 0;
                    player.currentRoundCost = roundAction.amount;
                    playerStatus[currentPlayerIndex] = PlayerStatus.ALLIN;
                    playerCost[currentPlayerIndex] = roundAction.amount;
                } else {
                    roundAction.originAmount = 0;
                    roundAction.amount = smallBlindValue * 2;
                    roundAction.bettingRounds = bettingRounds;
                    roundAction.action = Action.BET;
                    pot += roundAction.amount;
                    player.bankroll -= smallBlindValue;
                    player.currentRoundCost = roundAction.amount;
                    playerCost[currentPlayerIndex] = roundAction.amount;
                }
                roundActions.add(roundAction);
                player = getNextPlayer();
                currentRoundAdd = 2;
            }
            while (true) {

                // 判断游戏是否结束
                if (countPlayerPlaying()<=1) {
                    break;
                }

                if (playerStatus[currentPlayerIndex] != PlayerStatus.PLAYING) {
                    player = getNextPlayer();
                    continue;
                }

                if (lastAddPlayer == -1) {
                    lastAddPlayer = currentPlayerIndex;
                } else if(currentPlayerIndex == lastAddPlayer){
                    break;
                }

                RoundAction roundAction = player.action(bettingRounds, this, currentRoundAdd);
                if (roundAction.action == Action.FOLD) {
                    playerStatus[currentPlayerIndex] = PlayerStatus.FOLD;
                    player.playerStatus = PlayerStatus.FOLD;

                }else if(roundAction.action == Action.ALLIN){
                    playerStatus[currentPlayerIndex] = PlayerStatus.ALLIN;
                    player.playerStatus = PlayerStatus.ALLIN;
                    if (player.currentRoundCost > currentRoundAdd) {
                        currentRoundAdd = player.currentRoundCost;
                        lastAddPlayer = currentPlayerIndex;
                    }
                } else if (roundAction.action == Action.BET || roundAction.action == Action.RAISE
                        || roundAction.action == Action.RERAISE) {
                    currentRoundAdd = player.currentRoundCost;
                    lastAddPlayer = currentPlayerIndex;
                }

                pot += roundAction.amount;
                playerCost[currentPlayerIndex] += roundAction.amount;
                roundActions.add(roundAction);
                showRoundAction(roundActions);
                player = getNextPlayer();
            }
            // 判断游戏是否结束
            if (countPlayerPlaying()<=1) {
                break;
            }
        }


        //  计算底池归属
        int leftPlayerCount = countPlayerPlayingOrAllin();
        if (leftPlayerCount == 1) {
            // 只剩一个人， 底池都归他

        }else {
            // 比大小
            List<Player> ps = Arrays.asList(players);
            for (int i = 0; i < playerCount; i++) {
                players[i].calcBestCombination(this);
            }
            Collections.sort(ps, new Comparator<Player>() {
                @Override
                public int compare(Player o1, Player o2) {
                    if (o1 == null) {
                        return -1;
                    }
                    if (o2 == null) {
                        return 1;
                    }
                    return o1.bestCombination.compareTo(o2.bestCombination);
                }
            });
            for(int i =0;i<playerCount;i++) {
                if (players[i].bestCombination == null) {
                    System.out.println("位置 " + i + " 弃牌");
                } else {
                    System.out.println("位置 " + i + " 最终组合 " + players[i].bestCombination + " 排名 " + (ps.indexOf(players[i]) + 1));
                }

            }
            int[] playerGain = new int[playerCount];
            int tempPot = pot;
            for (Player player : ps) {
                int myCost = playerCost[player.index];
                int gain = 0;
                for(int i=0;i< playerCount;i++) {
                    int c = Math.min(myCost, playerCost[i]);
                    playerCost[i] -= c;
                    gain += c;
                    tempPot -= c;
                }
                player.bankroll += gain;// 赢得钱
                playerGain[player.index] = gain;
                if (tempPot < 0) {
                    // 报错
                    System.out.println("--------计算异常--------");
                    break;
                }
                if (tempPot == 0) {
                    break;
                }

            }
            System.out.println("最后底池分配："+Arrays.toString(playerGain));
        }


    }

    public int countPlayerPlaying() {
        int count = 0;
        for(int i=0;i< playerCount;i++) {
            if (playerStatus[i] == PlayerStatus.PLAYING) {
                count ++;
            }
        }
        return count;
    }

    public int countPlayerPlayingOrAllin() {
        int count = 0;
        for(int i=0;i< playerCount;i++) {
            if (playerStatus[i] == PlayerStatus.PLAYING || playerStatus[i] == PlayerStatus.ALLIN) {
                count ++;
            }
        }
        return count;
    }

    public Player getNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % playerCount;
        return players[currentPlayerIndex];
    }

}
