--
-- PostgreSQL database dump
--

-- Dumped from database version 17.3
-- Dumped by pg_dump version 17.3

-- Started on 2025-09-18 10:54:52

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 220 (class 1259 OID 17241)
-- Name: administrador; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.administrador (
    id integer NOT NULL,
    usuario character varying(50) NOT NULL,
    contrasena character varying(100) NOT NULL,
    persona_id integer
);


ALTER TABLE public.administrador OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 17240)
-- Name: administrador_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.administrador_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.administrador_id_seq OWNER TO postgres;

--
-- TOC entry 5005 (class 0 OID 0)
-- Dependencies: 219
-- Name: administrador_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.administrador_id_seq OWNED BY public.administrador.id;


--
-- TOC entry 230 (class 1259 OID 17312)
-- Name: cita; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cita (
    idcita integer NOT NULL,
    fecha date NOT NULL,
    estado character varying(20),
    motivo character varying(200),
    paciente_id integer,
    doctor_id integer,
    secretaria_id integer,
    expediente_id integer,
    hora time with time zone
);


ALTER TABLE public.cita OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 17311)
-- Name: cita_idcita_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cita_idcita_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.cita_idcita_seq OWNER TO postgres;

--
-- TOC entry 5006 (class 0 OID 0)
-- Dependencies: 229
-- Name: cita_idcita_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cita_idcita_seq OWNED BY public.cita.idcita;


--
-- TOC entry 232 (class 1259 OID 17339)
-- Name: consulta; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.consulta (
    idconsulta integer NOT NULL,
    cita_id integer,
    sintomas text,
    diagnostico text,
    tratamiento text,
    observaciones text,
    fechaconsulta date DEFAULT CURRENT_DATE
);


ALTER TABLE public.consulta OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 17338)
-- Name: consulta_idconsulta_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.consulta_idconsulta_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.consulta_idconsulta_seq OWNER TO postgres;

--
-- TOC entry 5007 (class 0 OID 0)
-- Dependencies: 231
-- Name: consulta_idconsulta_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.consulta_idconsulta_seq OWNED BY public.consulta.idconsulta;


--
-- TOC entry 226 (class 1259 OID 17283)
-- Name: doctor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.doctor (
    id integer NOT NULL,
    persona_id integer,
    licenciamedica character varying(50),
    usuario character varying(50),
    contrasena character varying(100),
    especialidad character varying(100) NOT NULL
);


ALTER TABLE public.doctor OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 17282)
-- Name: doctor_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.doctor_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.doctor_id_seq OWNER TO postgres;

--
-- TOC entry 5008 (class 0 OID 0)
-- Dependencies: 225
-- Name: doctor_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.doctor_id_seq OWNED BY public.doctor.id;


--
-- TOC entry 228 (class 1259 OID 17299)
-- Name: expediente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.expediente (
    idexpediente integer NOT NULL,
    paciente_id integer,
    fechacreacion date DEFAULT CURRENT_DATE,
    secretaria_id integer
);


ALTER TABLE public.expediente OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 17298)
-- Name: expediente_idexpediente_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.expediente_idexpediente_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.expediente_idexpediente_seq OWNER TO postgres;

--
-- TOC entry 5009 (class 0 OID 0)
-- Dependencies: 227
-- Name: expediente_idexpediente_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.expediente_idexpediente_seq OWNED BY public.expediente.idexpediente;


--
-- TOC entry 222 (class 1259 OID 17255)
-- Name: paciente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.paciente (
    id integer NOT NULL,
    persona_id integer,
    numerosegurosocial character varying(30),
    tiposangre character varying(5),
    alergias character varying(300)
);


ALTER TABLE public.paciente OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 17254)
-- Name: paciente_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.paciente_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.paciente_id_seq OWNER TO postgres;

--
-- TOC entry 5010 (class 0 OID 0)
-- Dependencies: 221
-- Name: paciente_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.paciente_id_seq OWNED BY public.paciente.id;


--
-- TOC entry 218 (class 1259 OID 17232)
-- Name: persona; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.persona (
    id integer NOT NULL,
    nombre character varying(100) NOT NULL,
    apellido character varying(100) NOT NULL,
    telefono character varying(20),
    email character varying(100),
    fechanacimiento date,
    direccion character varying(200)
);


ALTER TABLE public.persona OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 17231)
-- Name: persona_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.persona_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.persona_id_seq OWNER TO postgres;

--
-- TOC entry 5011 (class 0 OID 0)
-- Dependencies: 217
-- Name: persona_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.persona_id_seq OWNED BY public.persona.id;


--
-- TOC entry 234 (class 1259 OID 17354)
-- Name: receta; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.receta (
    idreceta integer NOT NULL,
    consulta_id integer,
    paciente_id integer,
    doctor_id integer,
    fecha date DEFAULT CURRENT_DATE,
    signosvitales text,
    parametrosantropometricos text,
    medicamentos text,
    indicaciones text
);


ALTER TABLE public.receta OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 17353)
-- Name: receta_idreceta_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.receta_idreceta_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.receta_idreceta_seq OWNER TO postgres;

--
-- TOC entry 5012 (class 0 OID 0)
-- Dependencies: 233
-- Name: receta_idreceta_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.receta_idreceta_seq OWNED BY public.receta.idreceta;


--
-- TOC entry 224 (class 1259 OID 17267)
-- Name: secretaria; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.secretaria (
    id integer NOT NULL,
    persona_id integer,
    idempleado character varying(30),
    usuario character varying(50),
    contrasena character varying(100)
);


ALTER TABLE public.secretaria OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 17266)
-- Name: secretaria_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.secretaria_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.secretaria_id_seq OWNER TO postgres;

--
-- TOC entry 5013 (class 0 OID 0)
-- Dependencies: 223
-- Name: secretaria_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.secretaria_id_seq OWNED BY public.secretaria.id;


--
-- TOC entry 4783 (class 2604 OID 17244)
-- Name: administrador id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.administrador ALTER COLUMN id SET DEFAULT nextval('public.administrador_id_seq'::regclass);


--
-- TOC entry 4789 (class 2604 OID 17315)
-- Name: cita idcita; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cita ALTER COLUMN idcita SET DEFAULT nextval('public.cita_idcita_seq'::regclass);


--
-- TOC entry 4790 (class 2604 OID 17342)
-- Name: consulta idconsulta; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.consulta ALTER COLUMN idconsulta SET DEFAULT nextval('public.consulta_idconsulta_seq'::regclass);


--
-- TOC entry 4786 (class 2604 OID 17286)
-- Name: doctor id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctor ALTER COLUMN id SET DEFAULT nextval('public.doctor_id_seq'::regclass);


--
-- TOC entry 4787 (class 2604 OID 17302)
-- Name: expediente idexpediente; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.expediente ALTER COLUMN idexpediente SET DEFAULT nextval('public.expediente_idexpediente_seq'::regclass);


--
-- TOC entry 4784 (class 2604 OID 17258)
-- Name: paciente id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.paciente ALTER COLUMN id SET DEFAULT nextval('public.paciente_id_seq'::regclass);


--
-- TOC entry 4782 (class 2604 OID 17235)
-- Name: persona id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persona ALTER COLUMN id SET DEFAULT nextval('public.persona_id_seq'::regclass);


--
-- TOC entry 4792 (class 2604 OID 17357)
-- Name: receta idreceta; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.receta ALTER COLUMN idreceta SET DEFAULT nextval('public.receta_idreceta_seq'::regclass);


--
-- TOC entry 4785 (class 2604 OID 17270)
-- Name: secretaria id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.secretaria ALTER COLUMN id SET DEFAULT nextval('public.secretaria_id_seq'::regclass);


--
-- TOC entry 4985 (class 0 OID 17241)
-- Dependencies: 220
-- Data for Name: administrador; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.administrador (id, usuario, contrasena, persona_id) FROM stdin;
1	garrix	admin	11
\.


--
-- TOC entry 4995 (class 0 OID 17312)
-- Dependencies: 230
-- Data for Name: cita; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cita (idcita, fecha, estado, motivo, paciente_id, doctor_id, secretaria_id, expediente_id, hora) FROM stdin;
13	2025-09-20	0	Chequeo general	1	1	1	\N	\N
14	2025-09-21	0	Dolor de cabeza	2	2	1	\N	\N
16	2025-09-18	Realizada	Chequeo Médico	3	3	\N	\N	11:00:00-06
15	2025-09-22	Realizada	Revisión piel	3	3	1	\N	\N
\.


--
-- TOC entry 4997 (class 0 OID 17339)
-- Dependencies: 232
-- Data for Name: consulta; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.consulta (idconsulta, cita_id, sintomas, diagnostico, tratamiento, observaciones, fechaconsulta) FROM stdin;
1	\N					\N
2	\N	nnbs				\N
\.


--
-- TOC entry 4991 (class 0 OID 17283)
-- Dependencies: 226
-- Data for Name: doctor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.doctor (id, persona_id, licenciamedica, usuario, contrasena, especialidad) FROM stdin;
1	5	LIC-001	doc01	abc123	Cardiología
2	6	LIC-002	doc02	abc123	Pediatría
3	7	LIC-003	doc03	abc123	Dermatología
8	3	LIC-005	fernando	admin	Pediatría
9	1	LIC-008	diego	admin	General
\.


--
-- TOC entry 4993 (class 0 OID 17299)
-- Dependencies: 228
-- Data for Name: expediente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.expediente (idexpediente, paciente_id, fechacreacion, secretaria_id) FROM stdin;
\.


--
-- TOC entry 4987 (class 0 OID 17255)
-- Dependencies: 222
-- Data for Name: paciente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.paciente (id, persona_id, numerosegurosocial, tiposangre, alergias) FROM stdin;
1	2	NSS123456	O+	Ninguna
2	3	NSS123457	A-	Penicilina
3	4	NSS123458	B+	Ninguna
\.


--
-- TOC entry 4983 (class 0 OID 17232)
-- Dependencies: 218
-- Data for Name: persona; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.persona (id, nombre, apellido, telefono, email, fechanacimiento, direccion) FROM stdin;
1	Ana	López	555-1234	ana.lopez@email.com	1990-05-10	Calle Principal 123
2	Luis	Martínez	555-1002	luis.martinez@email.com	1985-03-15	Avenida Reforma 456
3	Carla	Gómez	555-1003	carla.gomez@email.com	1992-07-20	Calle Juárez 789
4	Jorge	Pérez	555-1004	jorge.perez@email.com	1988-12-01	Boulevard Central 101
5	María	Fernández	555-1005	maria.fernandez@email.com	1995-09-30	Calle Hidalgo 202
6	Pedro	Santos	555-1006	pedro.santos@email.com	1983-11-11	Avenida Morelos 303
7	Lucía	Ramírez	555-1007	lucia.ramirez@email.com	1991-02-25	Calle Independencia 404
8	Diego	Torres	555-1008	diego.torres@email.com	1989-06-18	Boulevard Reforma 505
9	Sofía	Vargas	555-1009	sofia.vargas@email.com	1993-08-09	Calle Benito Juárez 606
10	Fernando	Rojas	555-1010	fernando.rojas@email.com	1987-01-22	Avenida Revolución 707
11	Abigail	Cano Cortes	9541857556	garrixcano7@gmail.com	2004-06-23	Los Naranjos Sta. Ma. Col.
12	Mateo	Sanchez	9542376789	sanchez@gmail.com	2006-09-14	desconocido
\.


--
-- TOC entry 4999 (class 0 OID 17354)
-- Dependencies: 234
-- Data for Name: receta; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.receta (idreceta, consulta_id, paciente_id, doctor_id, fecha, signosvitales, parametrosantropometricos, medicamentos, indicaciones) FROM stdin;
\.


--
-- TOC entry 4989 (class 0 OID 17267)
-- Dependencies: 224
-- Data for Name: secretaria; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.secretaria (id, persona_id, idempleado, usuario, contrasena) FROM stdin;
1	1	EMP-001	secre01	12345
2	2	\N	sofia	admin
\.


--
-- TOC entry 5014 (class 0 OID 0)
-- Dependencies: 219
-- Name: administrador_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.administrador_id_seq', 1, true);


--
-- TOC entry 5015 (class 0 OID 0)
-- Dependencies: 229
-- Name: cita_idcita_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cita_idcita_seq', 16, true);


--
-- TOC entry 5016 (class 0 OID 0)
-- Dependencies: 231
-- Name: consulta_idconsulta_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.consulta_idconsulta_seq', 2, true);


--
-- TOC entry 5017 (class 0 OID 0)
-- Dependencies: 225
-- Name: doctor_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.doctor_id_seq', 9, true);


--
-- TOC entry 5018 (class 0 OID 0)
-- Dependencies: 227
-- Name: expediente_idexpediente_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.expediente_idexpediente_seq', 1, false);


--
-- TOC entry 5019 (class 0 OID 0)
-- Dependencies: 221
-- Name: paciente_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.paciente_id_seq', 3, true);


--
-- TOC entry 5020 (class 0 OID 0)
-- Dependencies: 217
-- Name: persona_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.persona_id_seq', 12, true);


--
-- TOC entry 5021 (class 0 OID 0)
-- Dependencies: 233
-- Name: receta_idreceta_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.receta_idreceta_seq', 1, false);


--
-- TOC entry 5022 (class 0 OID 0)
-- Dependencies: 223
-- Name: secretaria_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.secretaria_id_seq', 2, true);


--
-- TOC entry 4797 (class 2606 OID 17246)
-- Name: administrador administrador_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.administrador
    ADD CONSTRAINT administrador_pkey PRIMARY KEY (id);


--
-- TOC entry 4799 (class 2606 OID 17248)
-- Name: administrador administrador_usuario_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.administrador
    ADD CONSTRAINT administrador_usuario_key UNIQUE (usuario);


--
-- TOC entry 4817 (class 2606 OID 17317)
-- Name: cita cita_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cita
    ADD CONSTRAINT cita_pkey PRIMARY KEY (idcita);


--
-- TOC entry 4819 (class 2606 OID 17347)
-- Name: consulta consulta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.consulta
    ADD CONSTRAINT consulta_pkey PRIMARY KEY (idconsulta);


--
-- TOC entry 4809 (class 2606 OID 17290)
-- Name: doctor doctor_licenciamedica_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctor
    ADD CONSTRAINT doctor_licenciamedica_key UNIQUE (licenciamedica);


--
-- TOC entry 4811 (class 2606 OID 17288)
-- Name: doctor doctor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctor
    ADD CONSTRAINT doctor_pkey PRIMARY KEY (id);


--
-- TOC entry 4813 (class 2606 OID 17292)
-- Name: doctor doctor_usuario_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctor
    ADD CONSTRAINT doctor_usuario_key UNIQUE (usuario);


--
-- TOC entry 4815 (class 2606 OID 17305)
-- Name: expediente expediente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.expediente
    ADD CONSTRAINT expediente_pkey PRIMARY KEY (idexpediente);


--
-- TOC entry 4801 (class 2606 OID 17260)
-- Name: paciente paciente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.paciente
    ADD CONSTRAINT paciente_pkey PRIMARY KEY (id);


--
-- TOC entry 4795 (class 2606 OID 17239)
-- Name: persona persona_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persona
    ADD CONSTRAINT persona_pkey PRIMARY KEY (id);


--
-- TOC entry 4821 (class 2606 OID 17362)
-- Name: receta receta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.receta
    ADD CONSTRAINT receta_pkey PRIMARY KEY (idreceta);


--
-- TOC entry 4803 (class 2606 OID 17274)
-- Name: secretaria secretaria_idempleado_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.secretaria
    ADD CONSTRAINT secretaria_idempleado_key UNIQUE (idempleado);


--
-- TOC entry 4805 (class 2606 OID 17272)
-- Name: secretaria secretaria_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.secretaria
    ADD CONSTRAINT secretaria_pkey PRIMARY KEY (id);


--
-- TOC entry 4807 (class 2606 OID 17276)
-- Name: secretaria secretaria_usuario_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.secretaria
    ADD CONSTRAINT secretaria_usuario_key UNIQUE (usuario);


--
-- TOC entry 4822 (class 2606 OID 17249)
-- Name: administrador administrador_persona_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.administrador
    ADD CONSTRAINT administrador_persona_id_fkey FOREIGN KEY (persona_id) REFERENCES public.persona(id) ON DELETE CASCADE;


--
-- TOC entry 4828 (class 2606 OID 17323)
-- Name: cita cita_doctor_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cita
    ADD CONSTRAINT cita_doctor_id_fkey FOREIGN KEY (doctor_id) REFERENCES public.doctor(id) ON DELETE CASCADE;


--
-- TOC entry 4829 (class 2606 OID 17333)
-- Name: cita cita_expediente_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cita
    ADD CONSTRAINT cita_expediente_id_fkey FOREIGN KEY (expediente_id) REFERENCES public.expediente(idexpediente);


--
-- TOC entry 4830 (class 2606 OID 17318)
-- Name: cita cita_paciente_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cita
    ADD CONSTRAINT cita_paciente_id_fkey FOREIGN KEY (paciente_id) REFERENCES public.paciente(id) ON DELETE CASCADE;


--
-- TOC entry 4831 (class 2606 OID 17328)
-- Name: cita cita_secretaria_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cita
    ADD CONSTRAINT cita_secretaria_id_fkey FOREIGN KEY (secretaria_id) REFERENCES public.secretaria(id);


--
-- TOC entry 4833 (class 2606 OID 17348)
-- Name: consulta consulta_cita_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.consulta
    ADD CONSTRAINT consulta_cita_id_fkey FOREIGN KEY (cita_id) REFERENCES public.cita(idcita) ON DELETE CASCADE;


--
-- TOC entry 4825 (class 2606 OID 17293)
-- Name: doctor doctor_persona_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctor
    ADD CONSTRAINT doctor_persona_id_fkey FOREIGN KEY (persona_id) REFERENCES public.persona(id) ON DELETE CASCADE;


--
-- TOC entry 4826 (class 2606 OID 17306)
-- Name: expediente expediente_paciente_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.expediente
    ADD CONSTRAINT expediente_paciente_id_fkey FOREIGN KEY (paciente_id) REFERENCES public.paciente(id) ON DELETE CASCADE;


--
-- TOC entry 4827 (class 2606 OID 17378)
-- Name: expediente expediente_secretaria_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.expediente
    ADD CONSTRAINT expediente_secretaria_id_fkey FOREIGN KEY (secretaria_id) REFERENCES public.secretaria(id);


--
-- TOC entry 4832 (class 2606 OID 17383)
-- Name: cita fk_secretaria; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cita
    ADD CONSTRAINT fk_secretaria FOREIGN KEY (secretaria_id) REFERENCES public.secretaria(id) ON DELETE SET NULL;


--
-- TOC entry 4823 (class 2606 OID 17261)
-- Name: paciente paciente_persona_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.paciente
    ADD CONSTRAINT paciente_persona_id_fkey FOREIGN KEY (persona_id) REFERENCES public.persona(id) ON DELETE CASCADE;


--
-- TOC entry 4834 (class 2606 OID 17363)
-- Name: receta receta_consulta_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.receta
    ADD CONSTRAINT receta_consulta_id_fkey FOREIGN KEY (consulta_id) REFERENCES public.consulta(idconsulta) ON DELETE CASCADE;


--
-- TOC entry 4835 (class 2606 OID 17373)
-- Name: receta receta_doctor_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.receta
    ADD CONSTRAINT receta_doctor_id_fkey FOREIGN KEY (doctor_id) REFERENCES public.doctor(id) ON DELETE CASCADE;


--
-- TOC entry 4836 (class 2606 OID 17368)
-- Name: receta receta_paciente_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.receta
    ADD CONSTRAINT receta_paciente_id_fkey FOREIGN KEY (paciente_id) REFERENCES public.paciente(id) ON DELETE CASCADE;


--
-- TOC entry 4824 (class 2606 OID 17277)
-- Name: secretaria secretaria_persona_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.secretaria
    ADD CONSTRAINT secretaria_persona_id_fkey FOREIGN KEY (persona_id) REFERENCES public.persona(id) ON DELETE CASCADE;


-- Completed on 2025-09-18 10:54:52

--
-- PostgreSQL database dump complete
--

