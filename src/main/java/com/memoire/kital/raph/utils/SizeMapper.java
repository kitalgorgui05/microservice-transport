package com.memoire.kital.raph.utils;

public class SizeMapper {
    private SizeMapper(){
        super();
    }

    public static final class SizChauffeurNomAndPrenom {
        private SizChauffeurNomAndPrenom(){
            super();
        }
        public static final int MIN=2;
        public static final int MAX=20;
    }

    public static final class SizChauffeurLieuNaissance {
        private SizChauffeurLieuNaissance(){
            super();
        }
        public static final int MIN=3;
        public static final int MAX=20;
    }
    public static final class SizChauffeurCin{
        private SizChauffeurCin(){
            super();
        }
        public static final int MIN=10;
        public static final int MAX=14;
    }

    public static final class SizChauffeurTelephone{
        private SizChauffeurTelephone(){
            super();
        }
        public static final int MIN=7;
        public static final int MAX=12;
    }

    public static final class SizeGroupeTransport{
        private SizeGroupeTransport(){
            super();
        }
        public static final int MIN=3;
        public static final int MAX=10;
    }
    public static final class SizeZone{
        private SizeZone(){
            super();
        }
        public static final int MIN=2;
        public static final int MAX=100;
    }

}
