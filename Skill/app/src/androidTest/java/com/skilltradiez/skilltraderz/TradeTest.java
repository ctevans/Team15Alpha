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


import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.List;

public class TradeTest extends ActivityInstrumentationTestCase2 {
    public TradeTest() {
        super(com.skilltradiez.skilltraderz.TradeTest.class);
    }

    public void testInitTrade() {
        UserDatabase db = new UserDatabase();
        db.deleteAllData();
        try {
            User user = db.createUser("u");
            User user2 = db.createUser("u2");
            List<Skill> offer = new ArrayList<Skill>();

            offer.add(new Skill(db, "illlllll", "LLLLLLLLLLLLLLLL"));

            Trade trade = user.getTradeList().createTrade(db, user, user2, offer);
            assertEquals(user.getTradeList().getMostRecentTrade(db), trade);
            assertEquals(user2.getTradeList().getMostRecentTrade(db), trade);
        } catch (UserAlreadyExistsException e) {
            assertTrue(false);
        }
    }

    public void testAcceptTradeRequest() {
        UserDatabase db = new UserDatabase();
        db.deleteAllData();
        try {
            User user = db.createUser("u");
            User user2 = db.createUser("u2");
            List<Skill> offer = new ArrayList<Skill>();

            offer.add(new Skill(db, "illlllll", "LLLLLLLLLLLLLLLL"));

            Trade trade = user.getTradeList().createTrade(db, user, user2, offer);
            trade.getHalfForUser(user2).setAccepted(true);
            assertTrue(!trade.isActive());
        } catch (UserAlreadyExistsException e) {
            assertTrue(false);
        }
    }

    public void testRefuseTradeRequest() {
        UserDatabase db = new UserDatabase();
        db.deleteAllData();
        try {
            User user = db.createUser("u");
            User user2 = db.createUser("u2");
            List<Skill> offer = new ArrayList<Skill>();

            offer.add(new Skill(db, "illlllll", "LLLLLLLLLLLLLLLL"));

            Trade trade = user.getTradeList().createTrade(db, user, user2, offer);
            // decline the trade
            trade.getHalfForUser(user2).setAccepted(false);
            assertTrue(!trade.isActive());
            // delete the trade
            user2.getTradeList().delete(user2.getTradeList().getMostRecentTrade(db));
            assertTrue(user.getTradeList().getActiveTrades(db).size() == 0);
        } catch (UserAlreadyExistsException e) {
            assertTrue(false);
        }
    }

    public void testCounterOfferTradeRequest() {
        UserDatabase db = new UserDatabase();
        db.deleteAllData();
        try {
            User user = db.createUser("u");
            User user2 = db.createUser("u2");
            List<Skill> offer = new ArrayList<Skill>();
            List<Skill> counterOffer = new ArrayList<Skill>();

            offer.add(new Skill(db,"illlllll", "LLLLLLLLLLLLLLLL"));
            counterOffer.add(new Skill(db,"Counter skill", "meta"));

            Trade trade = user.getTradeList().createTrade(db, user, user2, offer);
            trade.getHalfForUser(user2).setAccepted(false);
            trade.getHalfForUser(user2).setOffer(counterOffer);
            assertEquals(trade.getHalfForUser(user2).getOffer(), counterOffer);
        } catch (UserAlreadyExistsException e) {
            assertTrue(false);
        }
    }

    public void testModifyActiveTrade() {
        UserDatabase db = new UserDatabase();
        db.deleteAllData();
        try {
            User bob = db.createUser("Bob");
            User joel = db.createUser("Joel");

            List<Skill> skillz1 = new ArrayList<Skill>(), skillz2 = new ArrayList<Skill>();
            skillz1.add(new Skill(db, "...YEP"));

            TradeList tl = bob.getTradeList();
            tl.createTrade(db, bob, joel, skillz2);

            Trade t = tl.getMostRecentTrade(db);

            // Modify An Active Trade
            t.getHalfForUser(bob).setOffer(skillz1);
            assertEquals(t.getHalfForUser(bob).getOffer(), skillz1);

            // Delete An Active Trade
            tl.delete(t);
            assertTrue(tl.getActiveTrades(db).size() == 0);
        } catch (UserAlreadyExistsException e) {
            assertTrue(false);
        }
    }

    public void testBrowseTradeHistory() {
        UserDatabase db = new UserDatabase();
        db.deleteAllData();
        try {
            User bob = db.createUser("Bob");
            User joel = db.createUser("Joel");

            List<Skill> skillz1 = new ArrayList<Skill>(), skillz2 = new ArrayList<Skill>();
            skillz1.add(new Skill(db, "...YEP"));

            TradeList tl = bob.getTradeList();

            tl.createTrade(db, bob, joel, skillz2);
            Trade t1 = tl.getMostRecentTrade(db);

            tl.createTrade(db, bob, joel, skillz1);
            Trade t2 = tl.getMostRecentTrade(db);

            // Trade History Has been updated
            assertTrue(!t1.equals(t2));
        } catch (UserAlreadyExistsException e) {
            assertTrue(false);
        }
    }
}
