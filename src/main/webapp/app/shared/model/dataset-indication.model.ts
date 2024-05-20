export interface IDatasetIndication {
  id?: number;
  datasetIndicationId?: number;
  indication?: string;
  datasetIdId?: number;
}

export class DatasetIndication implements IDatasetIndication {
  constructor(
    public id?: number,
    public datasetIndicationId?: number,
    public indication?: string,
    public datasetIdId?: number,
  ) {}
}
