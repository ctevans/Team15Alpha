package com.skilltradiez.skilltraderz;
/*
 *    Team15Alpha
 *    AppName: SkillTradiez (Subject to change)
 *    Copyright (C) 2015  Stephen Andersen, Falon Scheers, Elyse Hill, Noah Weninger, Cole Evans
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * Trades are the core element of our application. Without the ability to create new trade
 * objects this application would become absolutely pointless. Trades are how the users interact
 * with one another and are the main driving and drawing appeal for the users to actually
 * USE our application.
 *
 * A trade object will possess attrbutes relating two users together as actors (actor1 and actor2)
 * and then will have a list of skills which are being offered for each user, and a boolean that
 * will maintain whether or not the trade is actually being accepted or not by the users!
 * As well as if it is already completed (and fully accepted) or not.
 *
 * In terms of the methods avaliable here, we have the various methods in which we will have the
 * ability to obtain the current actors in the trade (object of trade), get offers, set (new counter
 * offers) offers, set if accepted or declined or if it is even still active or not!
 */

import java.util.List;

/**
 * Neither Trade nor HalfTrade use notifyDB
 * because their commit methods are called by TradesList and Trade, respectively
 */
public class Trade extends Stringeable {
    private ID tradeID;
    private HalfTrade half1, half2;

    Trade(UserDatabase db, User user1, User user2) {
        tradeID = ID.generateRandomID();
        half1 = new HalfTrade(tradeID, user1.getUserID(), 1);
        half2 = new HalfTrade(tradeID, user2.getUserID(), 2);

        DatabaseController.addTrade(this);
    }

    public HalfTrade getHalf1() {
        return half1;
    }

    public HalfTrade getHalf2() {
        return half2;
    }

    public HalfTrade getOppositeHalf(User user) {
        if (half1.getUser().equals(user.getUserID())) {
            return half2;
        } else if (half2.getUser().equals(user.getUserID())) {
            return half1;
        }
        return null;
    }

    /**
     * Gets the parts of this trade corresponding to one of the involved users.
     * @return The half corresponding to the user, or null if the user is not involved in the trade.
     */
    public HalfTrade getHalfForUser(User user) {
        if (half1.getUser().equals(user.getUserID())) {
            return half1;
        } else if (half2.getUser().equals(user.getUserID())) {
            return half2;
        }
        return null;
    }

    public void set(UserDatabase userDB, User user1, User user2, List<Skill> offer, List<Skill> request){
        getHalfForUser(user1).setOffer(offer);
        getHalfForUser(user1).setAccepted(true);
        getHalfForUser(user2).setOffer(request);
        getHalfForUser(user2).setAccepted(false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trade trade = (Trade) o;

        return !(tradeID != null ? !tradeID.equals(trade.tradeID) : trade.tradeID != null);

    }

    @Override
    public int hashCode() {
        return tradeID != null ? tradeID.hashCode() : 0;
    }

    public ID getTradeID() {
        return tradeID;
    }

    /**
     * A trade is active if both parties have not yet accepted it.
     * @return
     */
    public boolean isActive() {
        return !(half1.isAccepted() && half2.isAccepted());
    }

    public boolean checkIfComplete() {
        if (half1.hasChanged() || half2.hasChanged())
            notifyDB();
        if (!isActive())
            MasterController.getCurrentUser().getTradeList().tradeComplete(tradeID);
        return true;
    }

    public boolean commit(UserDatabase userDB) {
        if (half1.hasChanged())
            if (!half1.commit(userDB))
                return false;
        if (half2.hasChanged())
            if (!half2.commit(userDB))
                return false;
        return true;
    }

    public String getName() {
        //TITLE
        return DatabaseController.getAccountByUserID(half1.getUser()).getProfile().getName() +
                " --> " +
                DatabaseController.getAccountByUserID(half2.getUser()).getProfile().getName();
    }

    public String getCategory(){
        if(isActive()){
            return "Active";
        }
        return "Inactive";
    }

    public String getDescription() {
        // SUBTITLE
        MasterController masterController = new MasterController();

        String desc1 = "", desc2 = "";
        List<Skill> offer = masterController.getSkillList(getHalf1().getOffer()),
                request = masterController.getSkillList(getHalf2().getOffer());

        if (!offer.isEmpty())
            desc1 = offer.get(0).getName();

        if (!request.isEmpty())
            desc2 = request.get(0).getName();

        for (int i=1;(i < 4) && ((i < offer.size()) || (i < request.size()));i++) {
            if (i < offer.size())
                desc1 += offer.get(i).getName() + ", ";
            if (i < request.size())
                desc2 += request.get(i).getName() + ", ";
        }

        if (offer.size() > 4) desc1 += "... ";
        if (request.size() > 4) desc2 += "...";

        return desc1 + "for " + desc2;
    }

    public Image getImage() {
        return new NullImage();
    }

    // The other users Trader Ranking
    public int getTop() {
        return DatabaseController.getAccountByUserID(getOppositeHalf(MasterController.getCurrentUser()).getUser()).getProfile().getTop();
    }

    @Override
    public String toString() {
        String string = half1.getUser().toString() + " " + half2.getUser().toString() + " ";

        // Skill Names: Might be a bit much
        for (ID skillID:half1.getOffer())
            string += DatabaseController.getSkillByID(skillID).getName() + " ";
        for (ID skillID:half2.getOffer())
            string += DatabaseController.getSkillByID(skillID).getName() + " ";

        return string;
    }
}
