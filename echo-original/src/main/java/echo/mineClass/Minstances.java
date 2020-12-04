/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package echo.mineClass;

import weka.core.Instances;

import java.io.BufferedReader;

//A single Minstance may belong to multiple Minstances set. So, any change in
//the instance will affect all the set of instances
public class Minstances extends Instances{
    public Minstances(Instances dataset, int size)
    {
        super(dataset, size);
    }

    public Minstances(BufferedReader in) throws Exception
    {
        super(in);
    }

    public void add(Minstance inst)
    {
        m_Instances.addElement(inst);
    }

    public Minstance minstance(int index)
    {
       return (Minstance)m_Instances.elementAt(index);
    }

    public Object[] getM_Instances() {
        return m_Instances.toArray();
    }
}
