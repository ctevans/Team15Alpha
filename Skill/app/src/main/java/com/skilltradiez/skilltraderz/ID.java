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

import java.io.Serializable;
import java.util.Random;

/**
 * BEFORE I EVEN BEGIN: This is a GENERAL ID THAT IS USED FOR
 * 1: USERS
 * 2: TRADES
 * 3: INVENTORIES
 *
 * This is used for ALL THREE. However in the description below I will use specific examples at
 * times. BUT DO NOT GET CONFUSED. THIS ID IS A GENERAL ID THAT COVERS ALL THREE OF THESE.
 *
 * An ID is going to be the absolutely essential core bit of information that this application
 * is going to utilize to identify the user to other users, trades, and inventories This is going
 * to be how the user, trade or inventory is going to be shown and will tie the user down to a
 * particular string of characters. That being said this is a core feature that is weaved through
 * the entire application making touches/references to multiple classes across multiple files.
 * This is the quissential identifier.
 *
 * In this object we have the private number that is going to be associated with the user and this
 * is going to be the arbitrarily assigned id of the user. This is not something that we exactly
 * want the user to care about, this is something that the application is going to care about.
 */

public class ID implements Serializable {

    /**Class Variables:
     * 1: id, this variable will hold the Long type of the identification number for this ID.
     *     Why we chose long opposed to int is that it allowed us greater flexibility in numbers.
     */
    private Long id;

    /**
     *  Will construct the ID Object with the Long number passed into the constructor.
     * @param num Long value input. Assigns this value to the Identification Number of the object.
     */
    ID(long num) {
        id = num;
    }

    /**
     * When invoked upon an instance of an ID object will return a string format of the ID.
     * @return String of the ID.
     */
    @Override
    public String toString() {
        return id.toString();
    }


    /**
     * Will return the current id number as a Number Object. We're getting fancy here. :)
     * @return Number Object.
     */
    public Number getID() {
        return id;
    }

    /**
     * Generates a random ID (Pseudorandom, because Java isn't truly random) and then creates
     * a NEW ID Object with this "randomly" generated ID value.
     * @return ID Object.
     */
    static ID generateRandomID() {
        return new ID(new Random().nextLong());
    }

    /**
     * Will take in an Object (any ol object...) of the Object type; and will then compare the
     * current object with the object passed into the method. If they are equal return true. If
     * they are not equal, then return false.
     * @param inputObject Object Object.
     * @return Boolean. True/False.
     */
    @Override
    public boolean equals(Object inputObject) {
        if (this == inputObject) return true;
        if (inputObject == null || getClass() != inputObject.getClass()) return false;

        ID id1 = (ID) inputObject;

        return !(id != null ? !id.equals(id1.id) : id1.id != null);

    }

    /**
     * This method when called is going to give us a hash (horrendous hash function- I am deeply
     * offended) of the tradeID ID Object value. Allowing us to easily compare values.
     * Why is that? Because a hash will make comparison very easy and small.
     *
     * @return Integer. THIS integer represents the hash of the tradeID ID object.
     */
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
