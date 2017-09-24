namespace Homework1.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class messagedetails : DbMigration
    {
        public override void Up()
        {
            CreateIndex("dbo.Messages", "ReceiverId");
            CreateIndex("dbo.Messages", "SenderId");
            CreateIndex("dbo.Messages", "RegionId");
            //AddForeignKey("dbo.Messages", "ReceiverId", "dbo.AspNetUsers", "Id");
            //AddForeignKey("dbo.Messages", "RegionId", "dbo.Regions", "RegionId");
            //AddForeignKey("dbo.Messages", "SenderId", "dbo.AspNetUsers", "Id");
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.Messages", "SenderId", "dbo.AspNetUsers");
            DropForeignKey("dbo.Messages", "RegionId", "dbo.Regions");
            DropForeignKey("dbo.Messages", "ReceiverId", "dbo.AspNetUsers");
            DropIndex("dbo.Messages", new[] { "RegionId" });
            DropIndex("dbo.Messages", new[] { "SenderId" });
            DropIndex("dbo.Messages", new[] { "ReceiverId" });
        }
    }
}
