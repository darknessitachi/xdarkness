#This controller shows how to generate a pie-chart by retrieving 
#values of the total output quantities produced in factories with their names
#On clicking on a pie-section, the detailed date-wise manufacture
#of each quantity produced by that factory is displayed as a chart on the same page.
#In this example, we show a combination of database + JavaScript (dataURL method)
#rendering using FusionCharts.
#The entire example can be summarized as under. This example shows the break-down
#of factory wise output generated. In a pie chart, we first show the sum of quantity
#generated by each factory. These pie slices, when clicked would show detailed date-wise
#output of that factory. The detailed data would be dynamically pulled by the column
#chart from another page. There are no page refreshes required. Everything
#is done on one single page.
#The XML data for the pie chart is fully created at run-time. Controller interacts
#with the database and creates the XML for this using Builder template.
#Now, for the column chart (date-wise output report), each time we need the data
#we dynamically submit request to the server with the appropriate factoryId. The server
#responds with an XML document, which we accept and update chart at client side.
class Fusioncharts::DbJsDataurlController < ApplicationController
  
    #Default action. The total output quantity for each factory is calculated using values from the database.
    #This data of factory id, name and total output is stored in an array for each factory. 
    #This array of arrays is visible to its view.
    def default
    
      headers["content-type"]="text/html";
      # Expects a parameter called animate
      @animate_chart = params[:animate]
      if @animate_chart==nil or @animate_chart.empty?
         # Assigns default values as '1'
         @animate_chart = '1'
      end  
      #Initialize the array
      @factory_data = []
      # Get the data in all the columns of FactoryMaster
      factory_masters = Fusioncharts::FactoryMaster. find(:all)
      # Loop through each factory obtained
      factory_masters.each do |factory_master|
          factory_id = factory_master.id
          factory_name = factory_master.name  
          total = 0.0
          factory_master.factory_output_quantities.each do |factory_output|
            # Calculate the total for this factory
            total = total + factory_output.quantity
          end
          @factory_data<<{:factory_id=>factory_id,:factory_name=>factory_name,:factory_output=>total}
      end  
    end
    
    #This action will generate a chart to show the detailed information of the selected factory.
    #Expects factoryId as parameter in the request 
    def factory_details
        headers["content-type"]="text/xml";
        # Get the factoryId from request using params[]
        @factory_id = params[:factoryId]
        #Initialize the array
        @factory_data = []
        
        factory_master = Fusioncharts::FactoryMaster. find(@factory_id)
                factory_master.factory_output_quantities.each do |factory_output|
                      date_of_production = factory_output.date_pro
                      # Formats the date to dd/mm without leading zeroes
                      formatted_date = format_date_remove_zeroes(date_of_production)
                      quantity_number = factory_output.quantity
                      @factory_data<<{:date_of_production=>formatted_date,:quantity_number=>quantity_number}
                end
    end
              
end