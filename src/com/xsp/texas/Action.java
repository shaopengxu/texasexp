package com.xsp.texas;

/**
 * Created by shpng on 2018/1/27.
 */
public enum Action {

    /**
     *
     Action 叫注/说话 - 一个玩家的决定。德克萨斯扑克里共有七种决定：
     Bet 押注 - 押上筹码
     Call 跟进 - 跟随众人押上同等的注额
     Fold 收牌 / 不跟 - 放弃继续牌局的机会
     Check 让牌 - 在无需跟进的情况下选择把决定“让”给下一位
     Raise 加注 - 把现有的注金抬高
     Re-raise 再加注 - 再别人加注以后回过来再加注
     All-in 全押 - 一次过把手上的筹码全押上
     */

    BET, CALL, FOLD, CHECK, RAISE, RERAISE, ALLIN
}
