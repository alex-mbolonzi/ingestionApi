export interface IDatasetDataCategory {
  id?: number;
  datasetDataCategoryId?: number;
  dataCategoryRef?: string;
  datasetIdId?: number;
}

export class DatasetDataCategory implements IDatasetDataCategory {
  constructor(
    public id?: number,
    public datasetDataCategoryId?: number,
    public dataCategoryRef?: string,
    public datasetIdId?: number,
  ) {}
}
