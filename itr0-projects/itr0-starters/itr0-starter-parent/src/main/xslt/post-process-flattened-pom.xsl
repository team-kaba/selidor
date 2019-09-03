<?xml version="1.0" encoding="utf-8"?>
<!-- noinspection CheckNodeTest -->
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:m="http://maven.apache.org/POM/4.0.0"
                exclude-result-prefixes="m">
  <xsl:output method="xml" encoding="utf-8" indent="yes" xslt:indent-amount="2" xmlns:xslt="http://xml.apache.org/xalan"/>
  <xsl:strip-space elements="*"/>
  <xsl:template match="@*|node()">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()"/>
    </xsl:copy>
  </xsl:template>
  <!-- flatten-maven-pluginで、propertiesの順番がおかしくなるので、アルファベット順でソートする。 -->
  <xsl:template match="/m:project/m:properties">
    <xsl:copy>
      <xsl:apply-templates select="node()">
        <xsl:sort select="name()"/>
      </xsl:apply-templates>
    </xsl:copy>
  </xsl:template>
  <!-- versionに${revision}と入っている場合、モジュール自体のversionと同じであることを意図しているので、モジュール自体のversionで置き換える。 -->
  <xsl:template
          match="/m:project/m:dependencyManagement/m:dependencies/m:dependency/m:version/text()[. = '${revision}']">
    <xsl:value-of select="/m:project/m:version/text()"/>
  </xsl:template>
  <!-- versionに${revision}と入っている場合、モジュール自体のversionと同じであることを意図しているので、モジュール自体のversionで置き換える。 -->
  <xsl:template
          match="/m:project/m:build/m:pluginManagement/m:plugins/m:plugin/m:version/text()[. = '${revision}']">
    <xsl:value-of select="/m:project/m:version/text()"/>
  </xsl:template>
  <xsl:template
          match="/m:project/m:build/m:pluginManagement/m:plugins/m:plugin/m:dependencies/m:dependency/m:version/text()[. = '${revision}']">
    <xsl:value-of select="/m:project/m:version/text()"/>
  </xsl:template>
  <!-- revisionはビルド用設定なので、デプロイ時には消す。 -->
  <xsl:template match="/m:project/m:properties/m:revision"/>
  <!-- main.basedirはビルド用設定なので、デプロイ時には消す。 -->
  <xsl:template match="/m:project/m:properties/m:main.basedir"/>
  <!-- distributionManagementは、これをparentにつかうアプリケーションに反映するべき設定ではないので消す。 -->
  <xsl:template match="/m:project/m:distributionManagement"/>
  <!-- relativePathは、これをparentにつかうアプリケーションに反映するべき設定ではないので消す。 -->
  <xsl:template match="/m:project/m:parent/m:relativePath"/>
  <!-- groupIdは、これをparentにつかうアプリケーションに反映するべき設定ではないので消す。 -->
  <xsl:template match="/m:project/m:groupId"/>
  <!-- versionは、これをparentにつかうアプリケーションに反映するべき設定ではないので消す。 -->
  <xsl:template match="/m:project/m:version"/>
  <!-- pluginsは、これをparentにつかうアプリケーションに反映するべき設定ではないので消す。 -->
  <xsl:template match="/m:project/m:build/m:plugins"/>
  <!-- licensesは、これをparentにつかうアプリケーションに反映するべき設定ではないので消す。 -->
  <xsl:template match="/m:project/m:licenses"/>
</xsl:stylesheet>
