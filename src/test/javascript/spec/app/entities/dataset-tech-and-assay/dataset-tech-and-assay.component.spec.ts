import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IngestionApiTestModule } from '../../../test.module';
import { DatasetTechAndAssayComponent } from 'app/entities/dataset-tech-and-assay/dataset-tech-and-assay.component';
import { DatasetTechAndAssayService } from 'app/entities/dataset-tech-and-assay/dataset-tech-and-assay.service';
import { DatasetTechAndAssay } from 'app/shared/model/dataset-tech-and-assay.model';

describe('Component Tests', () => {
  describe('DatasetTechAndAssay Management Component', () => {
    let comp: DatasetTechAndAssayComponent;
    let fixture: ComponentFixture<DatasetTechAndAssayComponent>;
    let service: DatasetTechAndAssayService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [DatasetTechAndAssayComponent],
        providers: [],
      })
        .overrideTemplate(DatasetTechAndAssayComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DatasetTechAndAssayComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DatasetTechAndAssayService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DatasetTechAndAssay(123)],
            headers,
          }),
        ),
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.datasetTechAndAssays[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
