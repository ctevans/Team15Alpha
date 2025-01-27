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

public class SkillTest extends ActivityInstrumentationTestCase2 {

    public SkillTest() {
        super(com.skilltradiez.skilltraderz.Skill.class);
    }

    public void testSetVisibility() {
        MasterController mc = new MasterController();
        mc.initializeController();
        UserDatabase db = mc.getUserDB();
        DatabaseController.deleteAllData();
        Skill skill = new Skill(db, "foo", "bar", "desc", true, new ArrayList<Image>());
        skill.setVisible(false);
        assertFalse(skill.isVisible());
    }

    public void testSetDescription() {
        MasterController mc = new MasterController();
        mc.initializeController();
        UserDatabase db = mc.getUserDB();
        DatabaseController.deleteAllData();
        Skill skill = new Skill(db, "foo", "bar", "desc", true, new ArrayList<Image>());
        skill.setDescription("DESCRIBE'D!!!");
        assertEquals(skill.getDescription(), "DESCRIBE'D!!!");
    }

    public void testAttachPicture() {
        MasterController mc = new MasterController();
        mc.initializeController();
        UserDatabase db = mc.getUserDB();
        DatabaseController.deleteAllData();
        Skill skill = new Skill(db, "foo", "bar", "desc", true, new ArrayList<Image>());
        Image img = new Image("foo.tiff");
        //image should be null initially
        assertTrue(skill.getImage() == new NullImage());
        //test setting image
        skill.setImage(img, 0);
        assertEquals(skill.getImage(), img);
    }

    public void testRetakePicture() {
        MasterController mc = new MasterController();
        mc.initializeController();
        UserDatabase db = mc.getUserDB();
        DatabaseController.deleteAllData();
        Skill skill = new Skill(db, "foo", "bar", "desc", true, new ArrayList<Image>());
        Image img = new Image("foo.tiff");
        Image img2 = new Image("foo.bmp");
        //test setting image
        skill.setImage(img, 0);
        assertEquals(skill.getImage(), img);
        //test changing(retaking) image
        skill.setImage(img2, 0);
        assertEquals(skill.getImage(), img2);
    }

    public void testDeletePicture() {
        MasterController mc = new MasterController();
        mc.initializeController();
        UserDatabase db = mc.getUserDB();
        DatabaseController.deleteAllData();
        Skill skill = new Skill(db, "foo", "bar", "desc", true, new ArrayList<Image>());
        Image img = new Image("foo.tiff");
        //test setting image
        skill.setImage(img, 0);
        assertEquals(skill.getImage(), img);
        //test deleting image
        skill.deleteImage(0);
        assertTrue(skill.getImage() == new NullImage());
    }
}
