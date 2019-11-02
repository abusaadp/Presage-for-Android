#                                               -*- Autoconf -*-
# Process this file with autoconf to produce a configure script.

AC_PREREQ([2.69])
AC_INIT([presage],[0.9.2~beta], [abusaadp@avaz.in])
AM_INIT_AUTOMAKE([1.9 tar-ustar -Wall])

AC_CONFIG_SRCDIR([lib/presage.cpp])
AC_CONFIG_HEADERS([config.h])

# Checks for programs.
AC_PROG_CXX
AC_PROG_CC
m4_ifdef([AM_PROG_AR], [AM_PROG_AR])

# ==================
# Checks for libtool
# ==================
LT_INIT([win32-dll])


# Checks for libraries.
# FIXME: Replace `main' with a function in `-lcurses':
AC_CHECK_LIB([curses], [main])
# FIXME: Replace `main' with a function in `-lm':
AC_CHECK_LIB([m], [main])
# FIXME: Replace `main' with a function in `-ltinyxml':
AC_CHECK_LIB([tinyxml], [main])

# Checks for header files.
AC_CHECK_HEADERS([stddef.h stdlib.h string.h unistd.h])

# ===================
# Checks for help2man
# ===================
AC_CHECK_PROG([HAVE_HELP2MAN], [help2man], [yes], [no])
if test "x$HAVE_HELP2MAN" = "xno"
then
    AC_MSG_NOTICE([help2man not found. Man documentation pages will not be built.])
fi
AM_CONDITIONAL([HAVE_HELP2MAN], [test "x$HAVE_HELP2MAN" = "xyes"])

if test "x$enable_sqlite" = "xyes" -a "x$have_sqlite" = "xyes"
then
    use_sqlite=yes
    AC_DEFINE([USE_SQLITE], [1], [Define to 1 if you want to use SQLite])
else
    use_sqlite=no
fi
AM_CONDITIONAL([USE_SQLITE], [test "x$use_sqlite" = "xyes"])

# ==================
# Checks for ncurses
# ==================
AC_CHECK_LIB([curses],
             [initscr],
             [have_curses_library=true],
	     [AC_MSG_WARN([curses library not found. curses demo programs will not be built.])])
AC_CHECK_HEADERS([curses.h],
                 [have_curses_header=true],
                 [AC_MSG_WARN([curses header file not found. curses demo programs will not be built.])],
                 [])
AM_CONDITIONAL([HAVE_CURSES], [test "x$have_curses_library" = "xtrue" -a "x$have_curses_header" = "xtrue"])

# ==================
# Checks for tinyxml
# ==================
AC_CHECK_LIB([tinyxml],
             [main],
             [have_tinyxml_library=true],
	     [AC_MSG_WARN([tinyxml library not found. Embedded tinyxml library will be built.])])
AC_CHECK_HEADERS([tinyxml.h],
                 [have_tinyxml_header=true],
                 [AC_MSG_WARN([tinyxml library not found. Embedded tinyxml library will be built.])],
                 [])
if test "x$have_tinyxml_library" != "xtrue" -o "x$have_tinyxml_header" != "xtrue"
then
    build_tinyxml=yes
else
    build_tinyxml=no
    AC_DEFINE([TIXML_USE_STL], [1], [Define to 1 to use STL string in TinyXML])
fi
AM_CONDITIONAL([BUILD_TINYXML], [test "x$build_tinyxml" = "xyes"])

save_ldflags="$LDFLAGS"
LDFLAGS="$LDFLAGS -Wl,--version-script,conftest.map"
AC_LINK_IFELSE(
	[AC_LANG_PROGRAM(
		[[const char hello_world[] = "Hello, World\n";]],
                [[]])],
	[have_ld_with_version_script=yes],
	[have_ld_with_version_script=no]
)
LDFLAGS="$save_ldflags"

if test "x$have_ld_with_version_script" = "xyes"
then
        AC_MSG_RESULT([yes])
else
        AC_MSG_RESULT([no])
        AC_MSG_WARN([Linker does not support version scripts.])
fi
AM_CONDITIONAL([HAVE_LD_WITH_VERSION_SCRIPT],
               [test "x$have_ld_with_version_script" = "xyes"])


# Checks for typedefs, structures, and compiler characteristics.
AC_CHECK_HEADER_STDBOOL
AC_C_INLINE
AC_TYPE_SIZE_T
AC_TYPE_UID_T
AC_CHECK_TYPES([ptrdiff_t])

# Checks for library functions.
AC_FUNC_MALLOC
AC_FUNC_REALLOC
AC_CHECK_FUNCS([memmove memset mkdir select strchr])
AC_CONFIG_MACRO_DIRS([m4])

AC_CONFIG_FILES([Makefile
                 lib/Makefile
                 lib/core/Makefile
                 lib/core/context_tracker/Makefile
                 lib/core/tokenizer/Makefile
                 lib/predictors/Makefile
                 lib/predictors/dbconnector/Makefile
                 lib/tinyxml/Makefile
                 tools/Makefile
                 tools/simulator/Makefile])
AC_OUTPUT
