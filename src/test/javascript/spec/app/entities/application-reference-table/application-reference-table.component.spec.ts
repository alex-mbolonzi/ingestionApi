import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IngestionApiTestModule } from '../../../test.module';
import { ApplicationReferenceTableComponent } from 'app/entities/application-reference-table/application-reference-table.component';
import { ApplicationReferenceTableService } from 'app/entities/application-reference-table/application-reference-table.service';
import { ApplicationReferenceTable } from 'app/shared/model/application-reference-table.model';

describe('Component Tests', () => {
  describe('ApplicationReferenceTable Management Component', () => {
    let comp: ApplicationReferenceTableComponent;
    let fixture: ComponentFixture<ApplicationReferenceTableComponent>;
    let service: ApplicationReferenceTableService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [ApplicationReferenceTableComponent],
        providers: [],
      })
        .overrideTemplate(ApplicationReferenceTableComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApplicationReferenceTableComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApplicationReferenceTableService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ApplicationReferenceTable(123)],
            headers,
          }),
        ),
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.applicationReferenceTables[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
