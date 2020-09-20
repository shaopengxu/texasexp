package com.xsp.texas;

/**
 *
 * 记录每个玩家的动作，raise call check fold
 * Created by shpng on 2018/1/27.
 */
public class RoundAction {

    /*

    Betting Rounds 押注圈 - 每一个牌局可分为四个押注圈。每一圈押注由按钮（庄家）左侧的玩家开始叫注。

    　Pre-flop 底牌权 / 前翻牌圈 - 公共牌出现以前的第一轮叫注。

　　Flop round 翻牌圈 - 首三张公共牌出现以后的押注圈。

　　Turn round 转牌圈 - 第四张公共牌出现以后的押注圈。

　　River round 河牌圈 - 第五张公共牌出现以后 ， 也即是摊牌以前的押注圈。


Action 叫注/说话 - 一个玩家的决定。德克萨斯扑克里共有七种决定：
   Bet 押注 - 押上筹码
   Call 跟进 - 跟随众人押上同等的注额
   Fold 收牌 / 不跟 - 放弃继续牌局的机会
   Check 让牌 - 在无需跟进的情况下选择把决定“让”给下一位
   Raise 加注 - 把现有的注金抬高
   Re-raise 再加注 - 再别人加注以后回过来再加注
   All-in 全押 - 一次过把手上的筹码全押上
     */

    public Action action ;
    public BettingRounds bettingRounds;
    public int originAmount; // 该轮原来已经加注的
    public int amount; // 加注额 或者 跟注额 或者 再加注额
    public int playerIndex;

}
