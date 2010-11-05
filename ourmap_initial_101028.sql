--
-- PostgreSQL database dump
--

-- Started on 2010-10-28 19:07:41

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;


CREATE TABLE actions (
    aid character(30) NOT NULL,
    label text,
    descr text
);


ALTER TABLE public.actions OWNER TO postgres;

--
-- TOC entry 1544 (class 1259 OID 18120)
-- Dependencies: 6
-- Name: categories; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE categories (
    cat_id integer NOT NULL,
    map_id integer NOT NULL,
    cat_name character(30),
    icon_id integer,
    created date,
    created_by text
);


ALTER TABLE public.categories OWNER TO postgres;

--
-- TOC entry 1545 (class 1259 OID 18126)
-- Dependencies: 6
-- Name: hotspot_objects; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE hotspot_objects (
    hotspot integer NOT NULL,
    object_id integer NOT NULL,
    index integer,
    block integer
);


ALTER TABLE public.hotspot_objects OWNER TO postgres;

--
-- TOC entry 1546 (class 1259 OID 18129)
-- Dependencies: 6
-- Name: hotspots; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE hotspots (
    hs_id integer NOT NULL,
    hs_pos point NOT NULL,
    hs_name text NOT NULL,
    hs_description text,
    hs_layer integer,
    altitude double precision,
    created timestamp with time zone,
    modified timestamp with time zone,
    created_by character(30),
    modified_by character(30),
    icon_id integer,
    keyword_id integer,
    display_mode integer,
    proximity_radius integer,
    creator_name text
);


ALTER TABLE public.hotspots OWNER TO postgres;

--
-- TOC entry 1547 (class 1259 OID 18135)
-- Dependencies: 6
-- Name: icons; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE icons (
    icon_id integer NOT NULL,
    filename text,
    icon_type smallint,
    icon_data bytea
);


ALTER TABLE public.icons OWNER TO postgres;

--
-- TOC entry 1548 (class 1259 OID 18141)
-- Dependencies: 6
-- Name: keywords; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE keywords (
    kw_id integer NOT NULL,
    map_id integer NOT NULL,
    kw_value character(50),
    cat_id integer,
    created_by character(30),
    created timestamp with time zone,
    icon_id integer,
    index integer
);


ALTER TABLE public.keywords OWNER TO postgres;

--
-- TOC entry 1549 (class 1259 OID 18144)
-- Dependencies: 6
-- Name: layers; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE layers (
    layer_id integer NOT NULL,
    layer_name character(120),
    layer_description text,
    map_id integer,
    created timestamp with time zone,
    modified timestamp with time zone,
    created_by character(30),
    modified_by character(30),
    index integer,
    icon_id integer
);


ALTER TABLE public.layers OWNER TO postgres;

--
-- TOC entry 1550 (class 1259 OID 18150)
-- Dependencies: 6
-- Name: maps; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE maps (
    mapid integer NOT NULL,
    mapname text,
    left_bottom point,
    right_top point,
    backmap text,
    zoom integer,
    center point,
    project_id integer,
    created timestamp with time zone,
    modified timestamp with time zone,
    created_by character(30),
    modified_by character(30),
    subtitle text,
    hotspots_mode smallint,
    description text,
    dis_ks boolean,
    icons_mode integer,
    privacy integer,
    hotspots_editable smallint,
    media_editable smallint,
    cats_enabled boolean,
    display_mode integer,
    creator_name text
);


ALTER TABLE public.maps OWNER TO postgres;

--
-- TOC entry 1551 (class 1259 OID 18156)
-- Dependencies: 6
-- Name: obj_categories; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE obj_categories (
    id integer NOT NULL,
    catname character(50)
);


ALTER TABLE public.obj_categories OWNER TO postgres;

--
-- TOC entry 1552 (class 1259 OID 18159)
-- Dependencies: 6
-- Name: objects; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE objects (
    object_id integer NOT NULL,
    obj_name character(255),
    obj_description text,
    obj_type integer,
    extension character(10),
    obj_size integer,
    created_by character(30),
    modified_by character(30),
    created timestamp with time zone,
    modified timestamp with time zone,
    obj_data bytea,
    status integer,
    obj_data2 bytea,
    label text,
    dimensions point,
    category integer,
    path text,
    creator_name text
);


ALTER TABLE public.objects OWNER TO postgres;

--
-- TOC entry 1553 (class 1259 OID 18165)
-- Dependencies: 6
-- Name: permissions; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE permissions (
    userid character(30) NOT NULL,
    element_id integer NOT NULL,
    element_type integer NOT NULL,
    ownership smallint,
    member smallint
);


ALTER TABLE public.permissions OWNER TO postgres;

--
-- TOC entry 1554 (class 1259 OID 18168)
-- Dependencies: 6
-- Name: projects; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE projects (
    id integer NOT NULL,
    nombre text,
    descr text,
    created timestamp with time zone,
    modified timestamp with time zone,
    created_by character(30),
    modified_by character(30),
    pwd character(10)
);


ALTER TABLE public.projects OWNER TO postgres;

--
-- TOC entry 1555 (class 1259 OID 18174)
-- Dependencies: 6
-- Name: rol; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE rol (
    rol character(10) NOT NULL,
    rolname character(50),
    weight integer
);


ALTER TABLE public.rol OWNER TO postgres;

--
-- TOC entry 1556 (class 1259 OID 18177)
-- Dependencies: 6
-- Name: rol_action; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE rol_action (
    rol character(10) NOT NULL,
    aid character(30) NOT NULL
);


ALTER TABLE public.rol_action OWNER TO postgres;

--
-- TOC entry 1557 (class 1259 OID 18180)
-- Dependencies: 6
-- Name: sq_categories; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE sq_categories
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 999999
    NO MINVALUE
    CACHE 1
    CYCLE;


ALTER TABLE public.sq_categories OWNER TO postgres;

--
-- TOC entry 1924 (class 0 OID 0)
-- Dependencies: 1557
-- Name: sq_categories; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('sq_categories', 103, true);


--
-- TOC entry 1558 (class 1259 OID 18182)
-- Dependencies: 6
-- Name: sq_hotspots; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE sq_hotspots
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 9999999
    NO MINVALUE
    CACHE 1
    CYCLE;


ALTER TABLE public.sq_hotspots OWNER TO postgres;

--
-- TOC entry 1925 (class 0 OID 0)
-- Dependencies: 1558
-- Name: sq_hotspots; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('sq_hotspots', 201, true);


--
-- TOC entry 1559 (class 1259 OID 18184)
-- Dependencies: 6
-- Name: sq_keywords; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE sq_keywords
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 999999
    NO MINVALUE
    CACHE 1
    CYCLE;


ALTER TABLE public.sq_keywords OWNER TO postgres;

--
-- TOC entry 1926 (class 0 OID 0)
-- Dependencies: 1559
-- Name: sq_keywords; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('sq_keywords', 174, true);


--
-- TOC entry 1560 (class 1259 OID 18186)
-- Dependencies: 6
-- Name: sq_layers; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE sq_layers
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 999999
    NO MINVALUE
    CACHE 1
    CYCLE;


ALTER TABLE public.sq_layers OWNER TO postgres;

--
-- TOC entry 1927 (class 0 OID 0)
-- Dependencies: 1560
-- Name: sq_layers; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('sq_layers', 247, true);


--
-- TOC entry 1561 (class 1259 OID 18188)
-- Dependencies: 6
-- Name: sq_maps; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE sq_maps
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 999999
    NO MINVALUE
    CACHE 1
    CYCLE;


ALTER TABLE public.sq_maps OWNER TO postgres;

--
-- TOC entry 1928 (class 0 OID 0)
-- Dependencies: 1561
-- Name: sq_maps; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('sq_maps', 291, true);


--
-- TOC entry 1562 (class 1259 OID 18190)
-- Dependencies: 6
-- Name: sq_objects; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE sq_objects
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 999999
    NO MINVALUE
    CACHE 1
    CYCLE;


ALTER TABLE public.sq_objects OWNER TO postgres;

--
-- TOC entry 1929 (class 0 OID 0)
-- Dependencies: 1562
-- Name: sq_objects; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('sq_objects', 2225, true);


--
-- TOC entry 1563 (class 1259 OID 18192)
-- Dependencies: 6
-- Name: sq_projects; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE sq_projects
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 999999
    NO MINVALUE
    CACHE 1
    CYCLE;


ALTER TABLE public.sq_projects OWNER TO postgres;

--
-- TOC entry 1930 (class 0 OID 0)
-- Dependencies: 1563
-- Name: sq_projects; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('sq_projects', 33, true);


--
-- TOC entry 1564 (class 1259 OID 18194)
-- Dependencies: 6
-- Name: sq_temp_objects; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE sq_temp_objects
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 999999
    NO MINVALUE
    CACHE 1
    CYCLE;


ALTER TABLE public.sq_temp_objects OWNER TO postgres;

--
-- TOC entry 1931 (class 0 OID 0)
-- Dependencies: 1564
-- Name: sq_temp_objects; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('sq_temp_objects', 404, true);


--
-- TOC entry 1565 (class 1259 OID 18196)
-- Dependencies: 6
-- Name: temp_objects; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE temp_objects (
    object_id integer NOT NULL,
    obj_name character(255),
    obj_description text,
    obj_type integer,
    extension character(10),
    obj_size integer,
    created_by character(30),
    modified_by character(30),
    created timestamp with time zone,
    modified timestamp with time zone,
    obj_data bytea,
    status integer,
    obj_data2 bytea,
    dimensions point,
    category integer,
    label text,
    path text,
    creator_name text
);


ALTER TABLE public.temp_objects OWNER TO postgres;

--
-- TOC entry 1566 (class 1259 OID 18202)
-- Dependencies: 6
-- Name: user_project; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE user_project (
    userid character(10) NOT NULL,
    project integer NOT NULL
);


ALTER TABLE public.user_project OWNER TO postgres;

--
-- TOC entry 1567 (class 1259 OID 18205)
-- Dependencies: 6
-- Name: user_rol; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE user_rol (
    userid character(30) NOT NULL,
    rol character(10) NOT NULL
);


ALTER TABLE public.user_rol OWNER TO postgres;

--
-- TOC entry 1568 (class 1259 OID 18208)
-- Dependencies: 6
-- Name: users; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE users (
    id character(30) NOT NULL,
    pwd text,
    nombre character(30),
    lastname character(30),
    descr text,
    created timestamp with time zone,
    modified timestamp with time zone,
    rol character(10),
    created_by character(30)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 1901 (class 0 OID 18114)
-- Dependencies: 1543
-- Data for Name: actions; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO actions (aid, label, descr) VALUES ('create_project                ', 'Create Project', 'Creates a project');
INSERT INTO actions (aid, label, descr) VALUES ('view_project                  ', 'View Project', 'View Project');
INSERT INTO actions (aid, label, descr) VALUES ('edit_roles                    ', 'Edit Roles', 'Edit roles');
INSERT INTO actions (aid, label, descr) VALUES ('admin                         ', 'Admin', 'View admin pages');
INSERT INTO actions (aid, label, descr) VALUES ('view_user                     ', 'View Users', 'View users profiles');
INSERT INTO actions (aid, label, descr) VALUES ('edit_project                  ', 'Edit Project', 'Edit Project');


--
-- TOC entry 1902 (class 0 OID 18120)
-- Dependencies: 1544
-- Data for Name: categories; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1903 (class 0 OID 18126)
-- Dependencies: 1545
-- Data for Name: hotspot_objects; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1904 (class 0 OID 18129)
-- Dependencies: 1546
-- Data for Name: hotspots; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1905 (class 0 OID 18135)
-- Dependencies: 1547
-- Data for Name: icons; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1906 (class 0 OID 18141)
-- Dependencies: 1548
-- Data for Name: keywords; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1907 (class 0 OID 18144)
-- Dependencies: 1549
-- Data for Name: layers; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1908 (class 0 OID 18150)
-- Dependencies: 1550
-- Data for Name: maps; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1909 (class 0 OID 18156)
-- Dependencies: 1551
-- Data for Name: obj_categories; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1910 (class 0 OID 18159)
-- Dependencies: 1552
-- Data for Name: objects; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO objects (object_id, obj_name, obj_description, obj_type, extension, obj_size, created_by, modified_by, created, modified, obj_data, status, obj_data2, label, dimensions, category, path, creator_name) VALUES (999992204, '2094.png                                                                                                                                                                                                                                                       ', NULL, 4, 'png       ', 1558, '1                             ', NULL, '2010-10-28 18:30:11.125-05', '2010-10-28 18:30:11.125-05', NULL, NULL, NULL, NULL, NULL, NULL, 'icons/2204.png', 'guardian');
INSERT INTO objects (object_id, obj_name, obj_description, obj_type, extension, obj_size, created_by, modified_by, created, modified, obj_data, status, obj_data2, label, dimensions, category, path, creator_name) VALUES (999992205, '2095.png                                                                                                                                                                                                                                                       ', NULL, 4, 'png       ', 1217, '1                             ', NULL, '2010-10-28 18:33:02.281-05', '2010-10-28 18:33:02.281-05', NULL, NULL, NULL, NULL, NULL, NULL, 'icons/2205.png', 'guardian');
INSERT INTO objects (object_id, obj_name, obj_description, obj_type, extension, obj_size, created_by, modified_by, created, modified, obj_data, status, obj_data2, label, dimensions, category, path, creator_name) VALUES (999992206, '2096.png                                                                                                                                                                                                                                                       ', NULL, 4, 'png       ', 1856, '1                             ', NULL, '2010-10-28 18:34:16.218-05', '2010-10-28 18:34:16.218-05', NULL, NULL, NULL, NULL, NULL, NULL, 'icons/2206.png', 'guardian');
INSERT INTO objects (object_id, obj_name, obj_description, obj_type, extension, obj_size, created_by, modified_by, created, modified, obj_data, status, obj_data2, label, dimensions, category, path, creator_name) VALUES (999992207, '2097.png                                                                                                                                                                                                                                                       ', NULL, 4, 'png       ', 2052, '1                             ', NULL, '2010-10-28 18:34:23.234-05', '2010-10-28 18:34:23.234-05', NULL, NULL, NULL, NULL, NULL, NULL, 'icons/2207.png', 'guardian');
INSERT INTO objects (object_id, obj_name, obj_description, obj_type, extension, obj_size, created_by, modified_by, created, modified, obj_data, status, obj_data2, label, dimensions, category, path, creator_name) VALUES (999992208, '2098.png                                                                                                                                                                                                                                                       ', NULL, 4, 'png       ', 1873, '1                             ', NULL, '2010-10-28 18:36:49.593-05', '2010-10-28 18:36:49.593-05', NULL, NULL, NULL, NULL, NULL, NULL, 'icons/2208.png', 'guardian');
INSERT INTO objects (object_id, obj_name, obj_description, obj_type, extension, obj_size, created_by, modified_by, created, modified, obj_data, status, obj_data2, label, dimensions, category, path, creator_name) VALUES (999992209, '2099.png                                                                                                                                                                                                                                                       ', NULL, 4, 'png       ', 1910, '1                             ', NULL, '2010-10-28 18:36:56.781-05', '2010-10-28 18:36:56.781-05', NULL, NULL, NULL, NULL, NULL, NULL, 'icons/2209.png', 'guardian');
INSERT INTO objects (object_id, obj_name, obj_description, obj_type, extension, obj_size, created_by, modified_by, created, modified, obj_data, status, obj_data2, label, dimensions, category, path, creator_name) VALUES (999992210, '2101.png                                                                                                                                                                                                                                                       ', NULL, 4, 'png       ', 1303, '1                             ', NULL, '2010-10-28 18:37:21.171-05', '2010-10-28 18:37:21.171-05', NULL, NULL, NULL, NULL, NULL, NULL, 'icons/2210.png', 'guardian');
INSERT INTO objects (object_id, obj_name, obj_description, obj_type, extension, obj_size, created_by, modified_by, created, modified, obj_data, status, obj_data2, label, dimensions, category, path, creator_name) VALUES (999992211, '2102.png                                                                                                                                                                                                                                                       ', NULL, 4, 'png       ', 1801, '1                             ', NULL, '2010-10-28 18:38:14.953-05', '2010-10-28 18:38:14.953-05', NULL, NULL, NULL, NULL, NULL, NULL, 'icons/2211.png', 'guardian');
INSERT INTO objects (object_id, obj_name, obj_description, obj_type, extension, obj_size, created_by, modified_by, created, modified, obj_data, status, obj_data2, label, dimensions, category, path, creator_name) VALUES (999992212, '2103.png                                                                                                                                                                                                                                                       ', NULL, 4, 'png       ', 2149, '1                             ', NULL, '2010-10-28 18:38:20.875-05', '2010-10-28 18:38:20.875-05', NULL, NULL, NULL, NULL, NULL, NULL, 'icons/2212.png', 'guardian');
INSERT INTO objects (object_id, obj_name, obj_description, obj_type, extension, obj_size, created_by, modified_by, created, modified, obj_data, status, obj_data2, label, dimensions, category, path, creator_name) VALUES (999992213, '2104.png                                                                                                                                                                                                                                                       ', NULL, 4, 'png       ', 2241, '1                             ', NULL, '2010-10-28 18:38:30.421-05', '2010-10-28 18:38:30.421-05', NULL, NULL, NULL, NULL, NULL, NULL, 'icons/2213.png', 'guardian');
INSERT INTO objects (object_id, obj_name, obj_description, obj_type, extension, obj_size, created_by, modified_by, created, modified, obj_data, status, obj_data2, label, dimensions, category, path, creator_name) VALUES (999992214, '2105.png                                                                                                                                                                                                                                                       ', NULL, 4, 'png       ', 1539, '1                             ', NULL, '2010-10-28 18:38:37.218-05', '2010-10-28 18:38:37.218-05', NULL, NULL, NULL, NULL, NULL, NULL, 'icons/2214.png', 'guardian');
INSERT INTO objects (object_id, obj_name, obj_description, obj_type, extension, obj_size, created_by, modified_by, created, modified, obj_data, status, obj_data2, label, dimensions, category, path, creator_name) VALUES (999992215, '2106.png                                                                                                                                                                                                                                                       ', NULL, 4, 'png       ', 1050, '1                             ', NULL, '2010-10-28 18:38:44.921-05', '2010-10-28 18:38:44.921-05', NULL, NULL, NULL, NULL, NULL, NULL, 'icons/2215.png', 'guardian');
INSERT INTO objects (object_id, obj_name, obj_description, obj_type, extension, obj_size, created_by, modified_by, created, modified, obj_data, status, obj_data2, label, dimensions, category, path, creator_name) VALUES (999992216, '2107.png                                                                                                                                                                                                                                                       ', NULL, 4, 'png       ', 2097, '1                             ', NULL, '2010-10-28 18:38:59.328-05', '2010-10-28 18:38:59.328-05', NULL, NULL, NULL, NULL, NULL, NULL, 'icons/2216.png', 'guardian');
INSERT INTO objects (object_id, obj_name, obj_description, obj_type, extension, obj_size, created_by, modified_by, created, modified, obj_data, status, obj_data2, label, dimensions, category, path, creator_name) VALUES (999992217, '2108.png                                                                                                                                                                                                                                                       ', NULL, 4, 'png       ', 1004, '1                             ', NULL, '2010-10-28 18:39:09.281-05', '2010-10-28 18:39:09.281-05', NULL, NULL, NULL, NULL, NULL, NULL, 'icons/2217.png', 'guardian');
INSERT INTO objects (object_id, obj_name, obj_description, obj_type, extension, obj_size, created_by, modified_by, created, modified, obj_data, status, obj_data2, label, dimensions, category, path, creator_name) VALUES (999992218, '2109.png                                                                                                                                                                                                                                                       ', NULL, 4, 'png       ', 2557, '1                             ', NULL, '2010-10-28 18:39:15.906-05', '2010-10-28 18:39:15.906-05', NULL, NULL, NULL, NULL, NULL, NULL, 'icons/2218.png', 'guardian');
INSERT INTO objects (object_id, obj_name, obj_description, obj_type, extension, obj_size, created_by, modified_by, created, modified, obj_data, status, obj_data2, label, dimensions, category, path, creator_name) VALUES (999992219, '2110.png                                                                                                                                                                                                                                                       ', NULL, 4, 'png       ', 1715, '1                             ', NULL, '2010-10-28 18:39:21.765-05', '2010-10-28 18:39:21.765-05', NULL, NULL, NULL, NULL, NULL, NULL, 'icons/2219.png', 'guardian');
INSERT INTO objects (object_id, obj_name, obj_description, obj_type, extension, obj_size, created_by, modified_by, created, modified, obj_data, status, obj_data2, label, dimensions, category, path, creator_name) VALUES (999992220, '2111.gif                                                                                                                                                                                                                                                       ', NULL, 4, 'gif       ', 146, '1                             ', NULL, '2010-10-28 18:39:28.187-05', '2010-10-28 18:39:28.187-05', NULL, NULL, NULL, NULL, NULL, NULL, 'icons/2220.gif', 'guardian');
INSERT INTO objects (object_id, obj_name, obj_description, obj_type, extension, obj_size, created_by, modified_by, created, modified, obj_data, status, obj_data2, label, dimensions, category, path, creator_name) VALUES (999992221, '2112.png                                                                                                                                                                                                                                                       ', NULL, 4, 'png       ', 1431, '1                             ', NULL, '2010-10-28 18:39:34.64-05', '2010-10-28 18:39:34.64-05', NULL, NULL, NULL, NULL, NULL, NULL, 'icons/2221.png', 'guardian');
INSERT INTO objects (object_id, obj_name, obj_description, obj_type, extension, obj_size, created_by, modified_by, created, modified, obj_data, status, obj_data2, label, dimensions, category, path, creator_name) VALUES (999992222, '2113.png                                                                                                                                                                                                                                                       ', NULL, 4, 'png       ', 1554, '1                             ', NULL, '2010-10-28 18:39:40.953-05', '2010-10-28 18:39:40.953-05', NULL, NULL, NULL, NULL, NULL, NULL, 'icons/2222.png', 'guardian');
INSERT INTO objects (object_id, obj_name, obj_description, obj_type, extension, obj_size, created_by, modified_by, created, modified, obj_data, status, obj_data2, label, dimensions, category, path, creator_name) VALUES (999992223, '2114.png                                                                                                                                                                                                                                                       ', NULL, 4, 'png       ', 1235, '1                             ', NULL, '2010-10-28 18:39:45.968-05', '2010-10-28 18:39:45.968-05', NULL, NULL, NULL, NULL, NULL, NULL, 'icons/2223.png', 'guardian');
INSERT INTO objects (object_id, obj_name, obj_description, obj_type, extension, obj_size, created_by, modified_by, created, modified, obj_data, status, obj_data2, label, dimensions, category, path, creator_name) VALUES (999992224, '2115.png                                                                                                                                                                                                                                                       ', NULL, 4, 'png       ', 1593, '1                             ', NULL, '2010-10-28 18:39:59.671-05', '2010-10-28 18:39:59.671-05', NULL, NULL, NULL, NULL, NULL, NULL, 'icons/2224.png', 'guardian');
INSERT INTO objects (object_id, obj_name, obj_description, obj_type, extension, obj_size, created_by, modified_by, created, modified, obj_data, status, obj_data2, label, dimensions, category, path, creator_name) VALUES (999992225, '2116.png                                                                                                                                                                                                                                                       ', NULL, 4, 'png       ', 2051, '1                             ', NULL, '2010-10-28 18:40:07.906-05', '2010-10-28 18:40:07.906-05', NULL, NULL, NULL, NULL, NULL, NULL, 'icons/2225.png', 'guardian');


--
-- TOC entry 1911 (class 0 OID 18165)
-- Dependencies: 1553
-- Data for Name: permissions; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1912 (class 0 OID 18168)
-- Dependencies: 1554
-- Data for Name: projects; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1913 (class 0 OID 18174)
-- Dependencies: 1555
-- Data for Name: rol; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO rol (rol, rolname, weight) VALUES ('admin     ', 'Teacher                                           ', 3);
INSERT INTO rol (rol, rolname, weight) VALUES ('overlord  ', 'Administrator                                     ', 10);
INSERT INTO rol (rol, rolname, weight) VALUES ('participa ', 'Student                                           ', 2);


--
-- TOC entry 1914 (class 0 OID 18177)
-- Dependencies: 1556
-- Data for Name: rol_action; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO rol_action (rol, aid) VALUES ('admin     ', 'view_project                  ');
INSERT INTO rol_action (rol, aid) VALUES ('admin     ', 'create_project                ');
INSERT INTO rol_action (rol, aid) VALUES ('admin     ', 'edit_project                  ');
INSERT INTO rol_action (rol, aid) VALUES ('participa ', 'view_project                  ');


--
-- TOC entry 1915 (class 0 OID 18196)
-- Dependencies: 1565
-- Data for Name: temp_objects; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1916 (class 0 OID 18202)
-- Dependencies: 1566
-- Data for Name: user_project; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1917 (class 0 OID 18205)
-- Dependencies: 1567
-- Data for Name: user_rol; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1918 (class 0 OID 18208)
-- Dependencies: 1568
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO users (id, pwd, nombre, lastname, descr, created, modified, rol, created_by) VALUES ('sys                           ', 'system', 'admin                         ', 'admin                         ', 'admin', NULL, NULL, NULL, NULL);


--
-- TOC entry 1847 (class 2606 OID 18215)
-- Dependencies: 1543 1543
-- Name: pk_action; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY actions
    ADD CONSTRAINT pk_action PRIMARY KEY (aid);


--
-- TOC entry 1849 (class 2606 OID 18217)
-- Dependencies: 1544 1544
-- Name: pk_cats; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY categories
    ADD CONSTRAINT pk_cats PRIMARY KEY (cat_id);


--
-- TOC entry 1853 (class 2606 OID 18219)
-- Dependencies: 1546 1546
-- Name: pk_hotspot; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY hotspots
    ADD CONSTRAINT pk_hotspot PRIMARY KEY (hs_id);


--
-- TOC entry 1851 (class 2606 OID 18221)
-- Dependencies: 1545 1545 1545
-- Name: pk_hotspot_objects; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY hotspot_objects
    ADD CONSTRAINT pk_hotspot_objects PRIMARY KEY (hotspot, object_id);


--
-- TOC entry 1855 (class 2606 OID 18223)
-- Dependencies: 1547 1547
-- Name: pk_icons; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY icons
    ADD CONSTRAINT pk_icons PRIMARY KEY (icon_id);


--
-- TOC entry 1857 (class 2606 OID 18225)
-- Dependencies: 1548 1548
-- Name: pk_kws; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY keywords
    ADD CONSTRAINT pk_kws PRIMARY KEY (kw_id);


--
-- TOC entry 1859 (class 2606 OID 18227)
-- Dependencies: 1549 1549
-- Name: pk_layer; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY layers
    ADD CONSTRAINT pk_layer PRIMARY KEY (layer_id);


--
-- TOC entry 1861 (class 2606 OID 18229)
-- Dependencies: 1550 1550
-- Name: pk_map; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY maps
    ADD CONSTRAINT pk_map PRIMARY KEY (mapid);


--
-- TOC entry 1863 (class 2606 OID 18231)
-- Dependencies: 1551 1551
-- Name: pk_obj_categories; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY obj_categories
    ADD CONSTRAINT pk_obj_categories PRIMARY KEY (id);


--
-- TOC entry 1865 (class 2606 OID 18233)
-- Dependencies: 1552 1552
-- Name: pk_objects; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY objects
    ADD CONSTRAINT pk_objects PRIMARY KEY (object_id);


--
-- TOC entry 1867 (class 2606 OID 18235)
-- Dependencies: 1553 1553 1553 1553
-- Name: pk_permission; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY permissions
    ADD CONSTRAINT pk_permission PRIMARY KEY (userid, element_id, element_type);


--
-- TOC entry 1869 (class 2606 OID 18237)
-- Dependencies: 1554 1554
-- Name: pk_project; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY projects
    ADD CONSTRAINT pk_project PRIMARY KEY (id);


--
-- TOC entry 1871 (class 2606 OID 18239)
-- Dependencies: 1555 1555
-- Name: pk_rol; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rol
    ADD CONSTRAINT pk_rol PRIMARY KEY (rol);


--
-- TOC entry 1873 (class 2606 OID 18241)
-- Dependencies: 1556 1556 1556
-- Name: pk_rol_action; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rol_action
    ADD CONSTRAINT pk_rol_action PRIMARY KEY (rol, aid);


--
-- TOC entry 1875 (class 2606 OID 18243)
-- Dependencies: 1565 1565
-- Name: pk_temp_objects; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY temp_objects
    ADD CONSTRAINT pk_temp_objects PRIMARY KEY (object_id);


--
-- TOC entry 1881 (class 2606 OID 18245)
-- Dependencies: 1568 1568
-- Name: pk_user; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT pk_user PRIMARY KEY (id);


--
-- TOC entry 1877 (class 2606 OID 18247)
-- Dependencies: 1566 1566 1566
-- Name: pk_userproject; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY user_project
    ADD CONSTRAINT pk_userproject PRIMARY KEY (userid, project);


--
-- TOC entry 1879 (class 2606 OID 18249)
-- Dependencies: 1567 1567 1567
-- Name: pk_userrol; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY user_rol
    ADD CONSTRAINT pk_userrol PRIMARY KEY (userid, rol);


--
-- TOC entry 1882 (class 2606 OID 18250)
-- Dependencies: 1864 1552 1544
-- Name: fk_category_icon; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY categories
    ADD CONSTRAINT fk_category_icon FOREIGN KEY (icon_id) REFERENCES objects(object_id);


--
-- TOC entry 1886 (class 2606 OID 18255)
-- Dependencies: 1864 1552 1546
-- Name: fk_hotspot_icon; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY hotspots
    ADD CONSTRAINT fk_hotspot_icon FOREIGN KEY (icon_id) REFERENCES objects(object_id);


--
-- TOC entry 1887 (class 2606 OID 18260)
-- Dependencies: 1546 1548 1856
-- Name: fk_hotspot_keyword; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY hotspots
    ADD CONSTRAINT fk_hotspot_keyword FOREIGN KEY (keyword_id) REFERENCES keywords(kw_id);


--
-- TOC entry 1888 (class 2606 OID 18265)
-- Dependencies: 1546 1858 1549
-- Name: fk_hotspot_layer; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY hotspots
    ADD CONSTRAINT fk_hotspot_layer FOREIGN KEY (hs_layer) REFERENCES layers(layer_id);


--
-- TOC entry 1884 (class 2606 OID 18270)
-- Dependencies: 1546 1545 1852
-- Name: fk_hotspot_objects_hotspot; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY hotspot_objects
    ADD CONSTRAINT fk_hotspot_objects_hotspot FOREIGN KEY (hotspot) REFERENCES hotspots(hs_id);


--
-- TOC entry 1885 (class 2606 OID 18275)
-- Dependencies: 1864 1552 1545
-- Name: fk_hotspot_objects_object; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY hotspot_objects
    ADD CONSTRAINT fk_hotspot_objects_object FOREIGN KEY (object_id) REFERENCES objects(object_id);


--
-- TOC entry 1889 (class 2606 OID 18280)
-- Dependencies: 1864 1552 1548
-- Name: fk_keyword_icon; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY keywords
    ADD CONSTRAINT fk_keyword_icon FOREIGN KEY (icon_id) REFERENCES objects(object_id);


--
-- TOC entry 1890 (class 2606 OID 18285)
-- Dependencies: 1548 1848 1544
-- Name: fk_kw_cat; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY keywords
    ADD CONSTRAINT fk_kw_cat FOREIGN KEY (cat_id) REFERENCES categories(cat_id);


--
-- TOC entry 1892 (class 2606 OID 18290)
-- Dependencies: 1552 1864 1549
-- Name: fk_layer_icon; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY layers
    ADD CONSTRAINT fk_layer_icon FOREIGN KEY (icon_id) REFERENCES objects(object_id);


--
-- TOC entry 1893 (class 2606 OID 18295)
-- Dependencies: 1549 1860 1550
-- Name: fk_layer_map; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY layers
    ADD CONSTRAINT fk_layer_map FOREIGN KEY (map_id) REFERENCES maps(mapid);


--
-- TOC entry 1894 (class 2606 OID 18300)
-- Dependencies: 1556 1543 1846
-- Name: fk_rolaction_action; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rol_action
    ADD CONSTRAINT fk_rolaction_action FOREIGN KEY (aid) REFERENCES actions(aid);


--
-- TOC entry 1895 (class 2606 OID 18305)
-- Dependencies: 1555 1556 1870
-- Name: fk_rolaction_rol; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rol_action
    ADD CONSTRAINT fk_rolaction_rol FOREIGN KEY (rol) REFERENCES rol(rol);


--
-- TOC entry 1900 (class 2606 OID 18310)
-- Dependencies: 1870 1555 1568
-- Name: fk_user_rol; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk_user_rol FOREIGN KEY (rol) REFERENCES rol(rol);


--
-- TOC entry 1896 (class 2606 OID 18315)
-- Dependencies: 1566 1554 1868
-- Name: fk_userproject_project; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_project
    ADD CONSTRAINT fk_userproject_project FOREIGN KEY (project) REFERENCES projects(id);


--
-- TOC entry 1897 (class 2606 OID 18320)
-- Dependencies: 1566 1568 1880
-- Name: fk_userproject_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_project
    ADD CONSTRAINT fk_userproject_user FOREIGN KEY (userid) REFERENCES users(id);


--
-- TOC entry 1898 (class 2606 OID 18325)
-- Dependencies: 1567 1555 1870
-- Name: fk_userrol_rol; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_rol
    ADD CONSTRAINT fk_userrol_rol FOREIGN KEY (rol) REFERENCES rol(rol);


--
-- TOC entry 1899 (class 2606 OID 18330)
-- Dependencies: 1880 1567 1568
-- Name: fk_userrol_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_rol
    ADD CONSTRAINT fk_userrol_user FOREIGN KEY (userid) REFERENCES users(id);


--
-- TOC entry 1883 (class 2606 OID 18335)
-- Dependencies: 1550 1544 1860
-- Name: pk_cat_map; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY categories
    ADD CONSTRAINT pk_cat_map FOREIGN KEY (map_id) REFERENCES maps(mapid);


--
-- TOC entry 1891 (class 2606 OID 18340)
-- Dependencies: 1548 1550 1860
-- Name: pk_kws_map; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY keywords
    ADD CONSTRAINT pk_kws_map FOREIGN KEY (map_id) REFERENCES maps(mapid);


--
-- TOC entry 1923 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2010-10-28 19:07:41

--
-- PostgreSQL database dump complete
--

