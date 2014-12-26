/*
 * Hivemall: Hive scalable Machine Learning Library
 *
 * Copyright (C) 2013-2014
 *   National Institute of Advanced Industrial Science and Technology (AIST)
 *   Registration Number: H25PRO-1520
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package hivemall.evaluation;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDAF;
import org.apache.hadoop.hive.ql.exec.UDAFEvaluator;
import org.apache.hadoop.hive.ql.metadata.HiveException;

@Description(name = "mae", value = "_FUNC_(predicted, actual) - Return a Mean Absolute Error")
public final class MeanAbsoluteErrorUDAF extends UDAF {

    public static class Evaluator implements UDAFEvaluator {

        private PartialResult partial;

        public Evaluator() {}

        @Override
        public void init() {
            this.partial = null;
        }

        public boolean iterate(double predicted, double actual) throws HiveException {
            if(partial == null) {
                this.partial = new PartialResult();
            }
            partial.iterate(predicted, actual);
            return true;
        }

        public PartialResult terminatePartial() {
            return partial;
        }

        public boolean merge(PartialResult other) throws HiveException {
            if(other == null) {
                return true;
            }
            if(partial == null) {
                this.partial = new PartialResult();
            }
            partial.merge(other);
            return true;
        }

        public double terminate() {
            if(partial == null) {
                return 0.d;
            }
            return partial.getMAE();
        }
    }

    public static class PartialResult {

        double diff_sum;
        long count;

        PartialResult() {
            this.diff_sum = 0d;
            this.count = 0L;
        }

        void iterate(double predicted, double actual) {
            diff_sum += Math.abs(predicted - actual);
            count++;
        }

        void merge(PartialResult other) {
            diff_sum += other.diff_sum;
            count += other.count;
        }

        double getMAE() {
            return diff_sum / count;
        }

    }

}
