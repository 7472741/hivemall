/*
 * Hivemall: Hive scalable Machine Learning Library
 *
 * Copyright (C) 2015 Makoto YUI
 * Copyright (C) 2013-2015 National Institute of Advanced Industrial Science and Technology (AIST)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package hivemall.utils.hadoop;

import org.junit.Assert;
import org.junit.Test;

public class Text2Test {

    @Test
    public void test() {
        byte[] expected = "this is a test".getBytes();
        Text2 text = new Text2(expected);
        Assert.assertArrayEquals(expected, text.getBytes());
        Assert.assertEquals(expected.length, text.getLength());
    }

}
