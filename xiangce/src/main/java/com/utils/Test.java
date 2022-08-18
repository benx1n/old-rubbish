package com.utils;

import com.entity.Photograph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Test {
    public void main1(String[] args) {


        List<user> list = new ArrayList<>();
        int size = list.size();
        int num = (int) Math.ceil(Double.valueOf(size) / 4);
        //判断num是否是偶数
        boolean isDouble = false;
        if (num % 2 == 0) {
            isDouble = true;
        }
        List<Show> shows = new LinkedList<>();

        int n = 0;
        for (int i = 0; i < list.size(); i++) {

            Show show = new Show();
            show.setImg("d://9012112.jpg");
            if (0 <= i && i < num) {//北
                int x = 0;
                int y = 0;
                int z = 0;
                int nx = 0;
                int nz = 1;
                if (isDouble) {
                    x = (-1000) * (num / 2) + (1000 * n) - 500;
                    z = ((1000 * num) / 2)+ 500;
                } else {
                    x = (-1000) * (num / 2) + (1000 * n);
                    z = (1000 * num) / 2 ;
                }
                show.setX(x);
                show.setY(y);
                show.setZ(z);
                show.setNx(nx);
                show.setNz(nz);
                shows.add(show);
            } else if (num <= i && i < num * 2) {//东
                int x = 0;
                int y = 0;
                int z = 0;
                int nx = -1;
                int nz = 0;
                if (isDouble) {
                    z =( (-1000) * (num / 2) + (1000 * n)) - 500;
                    x = ((1000 * num) / 2) + 500;
                } else {
                    z = (-1000) * (num / 2) + (1000 * n);
                    x = (1000 * num) / 2;
                }
                show.setX(x);
                show.setY(y);
                show.setZ(z);
                show.setNx(nx);
                show.setNz(nz);
                shows.add(show);

            } else if (num * 2 <= i && i < num * 3) {//南
                int x = 0;
                int y = 0;
                int z = 0;
                int nx = 0;
                int nz = -1;

                if (isDouble) {
                    x = (-1000) * (num / 2) + (1000 * n) - 500;
                    z = -((1000 * num) / 2) + 500;
                } else {
                    x = (-1000) * (num / 2) + (1000 * n);
                    z = -((1000 * num) / 2);
                }
                show.setX(x);
                show.setY(y);
                show.setZ(z);
                show.setNx(nx);
                show.setNz(nz);
                shows.add(show);

            } else if (num * 3 <= i && i < num * 5) {//西
                int x = 0;
                int y = 0;
                int z = 0;
                int nx = 1;
                int nz = 0;

                if (isDouble) {
                    z = (-1000) * (num / 2) + (1000 * n) - 500;
                    x = -((1000 * num) / 2) + 500;
                } else {
                    z = (-1000) * (num / 2) + (1000 * n);
                    x = -((1000 * num) / 2 );
                }
                show.setX(x);
                show.setY(y);
                show.setZ(z);
                show.setNx(nx);
                show.setNz(nz);
                shows.add(show);

            }
            n ++;
            if (n == num){
                n = 0;
            }
        }

        for (int i = 0; i < shows.size(); i++) {
            System.out.println(shows.get(i).toString());
        }
    }

    static class user {
        private int name;

        public user(int name) {
            this.name = name;
        }


        public int getName() {
            return name;
        }

        public void setName(int name) {
            this.name = name;
        }
    }

    static class Show {
        private String img;
        private int x;
        private int y;
        private int z;
        private int nx;
        private int nz;

        public Show() {

        }

        public Show(String img, int x, int y, int z, int nx, int nz) {
            this.img = img;
            this.x = x;
            this.y = y;
            this.z = z;
            this.nx = nx;
            this.nz = nz;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getZ() {
            return z;
        }

        public void setZ(int z) {
            this.z = z;
        }

        public int getNx() {
            return nx;
        }

        public void setNx(int nx) {
            this.nx = nx;
        }

        public int getNz() {
            return nz;
        }

        public void setNz(int nz) {
            this.nz = nz;
        }

        @Override
        public String toString() {
            return "{" +
                    "img='" + img + '\'' +
                    ", x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    ", nx=" + nx +
                    ", nz=" + nz +
                    '}';
        }
    }

    /*{img:'Images/8.jpg', x:-2000, y:0, z:2500, nx:0, nz:1},*/
    public static void main(String[] args) {
        Test test = new Test();
        test.main1(null);
    }

    /*{img:'Images/8.jpg', x:-2000,  y:0, z:-2500, nx:0, nz:-1},*/


    public static List<String> p(List<Photograph> list){



        int size = list.size();
        int num = (int) Math.ceil(Double.valueOf(size) / 4);
        //判断num是否是偶数
        boolean isDouble = false;
        if (num % 2 == 0) {
            isDouble = true;
        }
        List<Show> shows = new LinkedList<>();

        int n = 0;
        for (int i = 0; i < list.size(); i++) {

            Show show = new Show();
            show.setImg(list.get(i).getImage());
            if (0 <= i && i < num) {//北
                int x = 0;
                int y = 0;
                int z = 0;
                int nx = 0;
                int nz = 1;
                if (isDouble) {
                    x = (-1000) * ((num / 2) -1) + (1000 * n) - 500;
                    z = ((1000 * num) / 2);
                } else {
                    x = (-1000) * (num / 2) + (1000 * n);
                    z = (1000 * num) / 2 ;
                }
                show.setX(x);
                show.setY(y);
                show.setZ(z);
                show.setNx(nx);
                show.setNz(nz);
                shows.add(show);
            } else if (num <= i && i < num * 2) {//东
                int x = 0;
                int y = 0;
                int z = 0;
                int nx = -1;
                int nz = 0;
                if (isDouble) {
                    z =(-1000) * ((num / 2) -1) + (1000 * n) - 500;
                    x = ((1000 * num) / 2);
                } else {
                    z = (-1000) * (num / 2) + (1000 * n);
                    x = (1000 * num) / 2;
                }
                show.setX(x);
                show.setY(y);
                show.setZ(z);
                show.setNx(nx);
                show.setNz(nz);
                shows.add(show);

            } else if (num * 2 <= i && i < num * 3) {//南
                int x = 0;
                int y = 0;
                int z = 0;
                int nx = 0;
                int nz = -1;

                if (isDouble) {
                    x = (-1000) * ((num / 2) -1) + (1000 * n) - 500;
                    z = -((1000 * num) / 2);
                } else {
                    x = (-1000) * (num / 2) + (1000 * n);
                    z = -((1000 * num) / 2);
                }
                show.setX(x);
                show.setY(y);
                show.setZ(z);
                show.setNx(nx);
                show.setNz(nz);
                shows.add(show);

            } else if (num * 3 <= i && i < num * 5) {//西
                int x = 0;
                int y = 0;
                int z = 0;
                int nx = 1;
                int nz = 0;

                if (isDouble) {
                    z =(-1000) * ((num / 2) -1) + (1000 * n) - 500;
                    x = -((1000 * num) / 2);
                } else {
                    z = (-1000) * (num / 2) + (1000 * n);
                    x = -((1000 * num) / 2 );
                }
                show.setX(x);
                show.setY(y);
                show.setZ(z);
                show.setNx(nx);
                show.setNz(nz);
                shows.add(show);

            }
            n ++;
            if (n == num){
                n = 0;
            }
        }

        List<String> strs = new ArrayList<>();
        for (int i = 0; i < shows.size(); i++) {
            String str = "{img:'"+shows.get(i).getImg()+"', x:"+shows.get(i).getX()+", y:"+shows.get(i).getY()+", z:"+shows.get(i).getZ()+", nx:"+shows.get(i).getNx()+", nz:"+shows.get(i).getNz()+"},";
            System.out.println(str);
            strs.add(str);
        }
        return strs;
    }
}
